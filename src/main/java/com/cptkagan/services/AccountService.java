package com.cptkagan.services;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.cptkagan.models.Account;
import com.cptkagan.repositories.AccountRepository;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account save(Account account) {
        if (account.getPassword() != null) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            return accountRepository.save(account);
        } else {
            throw new IllegalArgumentException("Password Cannot Be Null!");
        }
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByUsername(username);
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole()));
        return new User(account.getUserName(), account.getPassword(), authorities);
    }
}
