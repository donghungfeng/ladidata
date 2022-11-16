package com.example.ladi.service.impl;

import com.example.ladi.config.AccountDetails;
import com.example.ladi.config.JwtTokenProvider;
import com.example.ladi.model.Account;
import com.example.ladi.repository.AccountRepository;
import com.example.ladi.repository.BaseRepository;
import com.example.ladi.service.AcountService;
import org.springframework.stereotype.Service;

@Service
public class AcountServiceImpl extends BaseServiceImpl<Account> implements AcountService {
    private AccountRepository acountRepository;

    public AcountServiceImpl(AccountRepository acountRepository){
        this.acountRepository = acountRepository;
    }
    @Override
    protected BaseRepository<Account> getRepository() {
        return acountRepository;
    }

    @Override
    public String login(String userName, String password) {
        Account account = acountRepository.findByUserName(userName);
        if (account == null){
            return "Account không tồn tại!";
        }
        if (!account.getPassWord().equals(password)){
            return "Mật khẩu không chính xác!";
        }
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        return jwtTokenProvider.generateToken(new AccountDetails(account));
    }
}
