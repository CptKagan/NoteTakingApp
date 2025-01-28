package com.cptkagan.seeddata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cptkagan.enums.Role;
import com.cptkagan.models.Account;
import com.cptkagan.models.Note;
import com.cptkagan.services.AccountService;
import com.cptkagan.services.NoteService;

@Component
public class SeedData implements CommandLineRunner{

    @Autowired
    private AccountService accountService;

    @Autowired
    private NoteService noteService;

    public void seed(){
        Account account = new Account();
        account.setEmail("account@gmail.com");
        account.setUserName("account1");
        account.setPassword("123456");
        account.setRole(Role.USER);
        accountService.save(account);

        Account account2 = new Account();
        account2.setEmail("account2@gmail.com");
        account2.setUserName("account2");
        account2.setPassword("123456");
        account2.setRole(Role.USER);
        accountService.save(account2);

        // Add notes for account1
    for (int i = 1; i <= 3; i++) {
        Note note = new Note();
        note.setTitle("Note " + i + " for account1");
        note.setBody("This is the content of note " + i);
        note.setAccount(account);
        noteService.save(note);
    }

    // Add notes for account2
    for (int i = 1; i <= 3; i++) {
        Note note = new Note();
        note.setTitle("Note " + i + " for account2");
        note.setBody("This is the content of note " + (i+3));
        note.setAccount(account2);
        noteService.save(note);
    }

    Account admin = new Account();
    admin.setUserName("admin123456");
    admin.setPassword("123456");
    admin.setEmail("admin@gmail.com");
    admin.setRole(Role.ADMIN);
    accountService.save(admin);

    }

    @Override
    public void run(String... args) throws Exception {
        seed();
    }
}
