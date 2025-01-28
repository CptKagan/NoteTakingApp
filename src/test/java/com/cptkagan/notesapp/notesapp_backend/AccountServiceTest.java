package com.cptkagan.notesapp.notesapp_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cptkagan.models.Account;
import com.cptkagan.repositories.AccountRepository;
import com.cptkagan.services.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testFindByUsername(){
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setEmail("email@gmail.com");
        account.setPassword("password");
        account.setUserName("account");

        Mockito.when(accountRepository.findByUserName(account.getUserName())).thenReturn(Optional.of(account));

        // Act
        Account result = accountService.findByUsername(account.getUserName());

        // Assert
        assertNotNull(result);
        assertEquals(account, result);
    }

    @Test
    public void testFindByUsernameNotFound(){
        // Arrange
        String username = "nonexistent";

        Mockito.when(accountRepository.findByUserName(username)).thenReturn(Optional.empty());

        // Act
        UsernameNotFoundException exception = null;
        try{
            accountService.findByUsername(username);
        }
        catch (UsernameNotFoundException e){
            exception = e;
        }

        // Assert
        assertNotNull(exception);
        assertEquals("User Not Found!", exception.getMessage());
    }
}
