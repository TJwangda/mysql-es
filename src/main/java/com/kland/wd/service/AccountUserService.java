package com.kland.wd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kland.wd.dto.AccountUser;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wd
 * @since 2021-03-04
 */
public interface AccountUserService extends IService<AccountUser> {
    int findMaxId();
}
