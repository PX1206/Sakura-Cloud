package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.tool.LoginUtil;
import com.sakura.user.entity.Merchant;
import com.sakura.user.entity.MerchantPermission;
import com.sakura.user.mapper.MerchantMapper;
import com.sakura.user.mapper.MerchantPermissionMapper;
import com.sakura.user.mapper.MerchantTypePermissionMapper;
import com.sakura.user.param.MerchantPermissionParam;
import com.sakura.user.service.MerchantPermissionService;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.user.vo.MerchantPermissionTreeVo;
import com.sakura.user.vo.MerchantPermissionVo;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商户权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantPermissionServiceImpl extends BaseServiceImpl<MerchantPermissionMapper, MerchantPermission> implements MerchantPermissionService {

    @Autowired
    private MerchantPermissionMapper merchantPermissionMapper;
    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private MerchantTypePermissionMapper merchantTypePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addMerchantPermission(MerchantPermissionParam merchantPermissionParam) throws Exception {
        // 获取商户数据
        Merchant merchant = merchantMapper.selectOne(
                Wrappers.<Merchant>lambdaQuery()
                        .eq(Merchant::getMerchantNo, merchantPermissionParam.getMerchantNo())
                        .ne(Merchant::getStatus, 0)
        );
        if (merchant == null) {
            throw new BusinessException(500, "商户信息异常");
        }
        // 根据商户类型获取基础权限
        Set<Integer> typePermissionIds = merchantTypePermissionMapper.findPermissionIdByTypeId(merchant.getTypeId());
        // 排除里面的基础权限获取商户特殊权限
        Set<Integer> merchantPermissionIds = merchantPermissionParam.getPermissionIds().stream()
                .filter(element -> !typePermissionIds.contains(element))
                .collect(Collectors.toSet());
        // 如果不为空说明商户有特殊权限，为空说明没有特殊权限则需要清空之前的特殊权限
        if (merchantPermissionIds.size() > 0) {
            // 获取之前的特殊权限
            Set<Integer> oldMerchantPermissionIds = merchantPermissionMapper.findPermissionIdByMerchantNo(merchantPermissionParam.getMerchantNo());
            if (oldMerchantPermissionIds.size() > 0) {
                // 找出新增的权限，之前没有的就是新增的权限
                Set<Integer> newAddPermissions = merchantPermissionIds.stream()
                        .filter(element -> !oldMerchantPermissionIds.contains(element))
                        .collect(Collectors.toSet());
                if (newAddPermissions.size() > 0) {
                    merchantPermissionMapper.saveMerchantPermission(merchantPermissionParam.getMerchantNo(), newAddPermissions);
                }
                // 找出删除的权限，原先有现在没有的权限
                Set<Integer> deletePermissions = oldMerchantPermissionIds.stream()
                        .filter(element -> !merchantPermissionIds.contains(element))
                        .collect(Collectors.toSet());
                if (deletePermissions.size() > 0) {
                    merchantPermissionMapper.deleteByPermissionsId(merchantPermissionParam.getMerchantNo(), deletePermissions);
                }
            } else {
                merchantPermissionMapper.saveMerchantPermission(merchantPermissionParam.getMerchantNo(), merchantPermissionIds);
            }
        } else {
            // 删除商户原有的特殊权限
            merchantPermissionMapper.deleteMerchantPermission(merchantPermissionParam.getMerchantNo());
        }

        return true;
    }

    @Override
    public List<MerchantPermissionVo> getMerchantPermission(String merchantNo) {
        return merchantPermissionMapper.findMerchantPermission(merchantNo);
    }

    @Override
    public List<MerchantPermissionTreeVo> getMerchantPermissionTree() throws Exception {
        // 先获取商户所有权限
        List<MerchantPermissionTreeVo> merchantPermissionTreeVos = merchantPermissionMapper.findMerchantPermissionTree(LoginUtil.getMerchantNo());
        // 将权限遍历成树
        int num = 0; // 添加一个遍历层数，防止数据异常导致递归死循环
        return getChildPermissions(0, merchantPermissionTreeVos, num);
    }

    /**
     * @description: 递归获取子权限
     * @param parentId 父ID
     * @param merchantPermissionTreeVos 所有权限集合
     * @param num 最大遍历层数
     * @author: Sakura
     * @date: 2023/11/16 16:52
     */
    private List<MerchantPermissionTreeVo> getChildPermissions(Integer parentId, List<MerchantPermissionTreeVo> merchantPermissionTreeVos, int num) throws Exception {
        num++;// 控制遍历次数，防止因数据问题导致无限循环内存溢出，目前最大支持5层
        if (num > 5) {
            log.error("权限信息异常parentId：" + parentId);
            throw new BusinessException(500, "权限数据异常，请联系管理人员");
        }

        List<MerchantPermissionTreeVo> resultList = new ArrayList<>();

        for (MerchantPermissionTreeVo merchantPermissionTreeVo : merchantPermissionTreeVos) {
            if (parentId.equals(merchantPermissionTreeVo.getParentId())) {
                // 通过当前ID获取子权限
                List<MerchantPermissionTreeVo> childPermissionTreeVos = getChildPermissions(merchantPermissionTreeVo.getId(), merchantPermissionTreeVos, num);
                merchantPermissionTreeVo.setChildList(childPermissionTreeVos);

                resultList.add(merchantPermissionTreeVo);
            }
        }

        return resultList;
    }

}
