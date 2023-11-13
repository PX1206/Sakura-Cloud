package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.tool.CommonUtil;
import com.sakura.common.tool.LoginUtil;
import com.sakura.common.tool.SHA256Util;
import com.sakura.common.tool.StringUtil;
import com.sakura.user.entity.Merchant;
import com.sakura.user.entity.MerchantUser;
import com.sakura.user.mapper.MerchantMapper;
import com.sakura.user.mapper.MerchantUserMapper;
import com.sakura.user.param.ApplySettled;
import com.sakura.user.param.DeleteMerchantParam;
import com.sakura.user.param.MerchantParam;
import com.sakura.user.service.MerchantService;
import com.sakura.user.param.MerchantPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.utils.MerchantUtil;
import com.sakura.user.vo.MerchantVo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * 商户表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantServiceImpl extends BaseServiceImpl<MerchantMapper, Merchant> implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MerchantUserMapper merchantUserMapper;

    @Autowired
    private MerchantUtil merchantUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean applySettled(ApplySettled applySettled) throws Exception {
        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(applySettled.getMobile(),
                applySettled.getSaltKey(), false);
        if (!StringUtil.isValidPhoneNumber(mobile)) {
            throw new BusinessException(500, "手机号格式异常");
        }
        applySettled.setMobile(mobile);

        // 校验短信验证码是否正确
        if (!commonUtil.checkCode(CommonConstant.SMS_CODE + applySettled.getMobile(),
                applySettled.getSmsCode())) {
            throw new BusinessException(500, "短信验证码错误");
        }

        // 获取一个商户号
        String merchantNo = merchantUtil.createMerchantNo();
        // 生成一个超级管理员信息
        // 这里和商户用户那里一样，存在同一个用户可能有多个商家，到时候登录的时候再做处理吧，比如选择一个商户登录等
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setMerchantNo(merchantNo);
        merchantUser.setName(applySettled.getUserName());
        merchantUser.setMobile(applySettled.getMobile());
        merchantUser.setUserId(StringUtil.random(32)); // 生成用户ID
        merchantUser.setSalt(SHA256Util.getSHA256Str(UUID.randomUUID().toString())); // 随机生成盐
        // 获取用户真实密码
        String password = commonUtil.getDecryptStr(applySettled.getPassword(), applySettled.getSaltKey(), null);
        // HA256S加密
        merchantUser.setPassword(SHA256Util.getSHA256Str(password + merchantUser.getSalt()));
        merchantUser.setStatus(1);
        // 以当前申请用户作为超级管理员，超级管理员可转让，但一个商户永远只能存在一个超管
        merchantUser.setSuperuser(1);

        merchantUserMapper.insert(merchantUser);

        // 生成商户信息
        // 新生成的商户无其它功能，需等完善资料提交审核通过后根据商户类型分配权限
        Merchant merchant = new Merchant();
        merchant.setName(applySettled.getMerchantName());
        merchant.setMerchantNo(merchantNo);
        // 顶层商户默认为0
        merchant.setParentNo("0");
        merchant.setStatus(1);
        // 默认为待提交状态
        merchant.setCheckStatus(1);

        merchantMapper.insert(merchant);

        return true;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchant(MerchantParam merchantParam) throws Exception {
        // 获取当前登录用户信息
        MerchantUser merchantUser = merchantUserMapper.selectOne(
                Wrappers.<MerchantUser>lambdaQuery()
                        .eq(MerchantUser::getUserId, LoginUtil.getUserId())
                        .eq(MerchantUser::getStatus, 1)
        );
        // 如果当前不是超管则拒绝操作
        if (merchantUser == null || merchantUser.getSuperuser() != 1) {
            throw new BusinessException(500, "非法操作！");
        }

        // 获取商户信息
        Merchant merchant = merchantMapper.selectOne(
                Wrappers.<Merchant>lambdaQuery()
                        .eq(Merchant::getMerchantNo, merchantUser.getMerchantNo())
                        .ne(Merchant::getStatus, 0)
        );
        if (merchant == null) {
            throw new BusinessException(500, "商户信息异常");
        }
        // 冻结状态不可修改
        if (merchant.getStatus() == 2) {
            throw new BusinessException(500, "商户已被冻结，无法修改信息");
        }
        // 审核通过不能修改信息
        if (merchant.getCheckStatus() == 3) {
            throw new BusinessException(500, "商户信息已通过审核，无法修改");
        }

        BeanUtils.copyProperties(merchantParam, merchant);
        merchant.setUpdateDt(new Date());

        return super.updateById(merchant);
    }

    @Override
    public MerchantVo getMerchant() throws Exception {
        // 获取商户信息
        Merchant merchant = merchantMapper.selectOne(
                Wrappers.<Merchant>lambdaQuery()
                        .eq(Merchant::getMerchantNo, LoginUtil.getMerchantNo())
                        .ne(Merchant::getStatus, 0)
        );
        if (merchant == null) {
            throw new BusinessException(500, "商户信息异常");
        }

        MerchantVo merchantVo = new MerchantVo();
        BeanUtils.copyProperties(merchant, merchantVo);

        return merchantVo;
    }

    @Override
    public MerchantVo getMerchant(String merchantNo) throws Exception {
        // 获取商户信息
        Merchant merchant = merchantMapper.selectOne(
                Wrappers.<Merchant>lambdaQuery()
                        .eq(Merchant::getMerchantNo, merchantNo)
        );
        if (merchant == null) {
            throw new BusinessException(500, "商户信息异常");
        }

        MerchantVo merchantVo = new MerchantVo();
        BeanUtils.copyProperties(merchant, merchantVo);

        return merchantVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchant(DeleteMerchantParam deleteMerchantParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(deleteMerchantParam.getKey(), deleteMerchantParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        // 获取商户信息
        Merchant merchant = merchantMapper.selectOne(
                Wrappers.<Merchant>lambdaQuery()
                        .eq(Merchant::getMerchantNo, deleteMerchantParam.getMerchantNo())
                        .ne(Merchant::getStatus, 0)
        );
        if (merchant == null) {
            throw new BusinessException(500, "商户信息异常");
        }

        // 将商户置为不可用状态，非物理删除
        merchant.setStatus(0);
        merchant.setUpdateDt(new Date());

        merchantMapper.updateById(merchant);
        // 删除商户后还需要删除所有商户用户账号，清除登录信息等
        // 调用异步方法，有时数据过多可能会导致请求时间很长
        merchantUtil.deleteMerchant(deleteMerchantParam.getMerchantNo());

        return true;
    }

    @Override
    public Paging<Merchant> getMerchantPageList(MerchantPageParam merchantPageParam) throws Exception {
        Page<Merchant> page = new PageInfo<>(merchantPageParam, OrderItem.desc(getLambdaColumn(Merchant::getCreateDt)));
        LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
        IPage<Merchant> iPage = merchantMapper.selectPage(page, wrapper);
        return new Paging<Merchant>(iPage);
    }

}
