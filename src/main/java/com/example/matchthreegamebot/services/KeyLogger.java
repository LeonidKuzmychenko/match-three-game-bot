package com.example.matchthreegamebot.services;


import com.example.matchthreegamebot.utils.Constants;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class KeyLogger implements NativeKeyListener {

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == 3639) {
            boolean currentValue = Constants.KEY_PRESS_STATE.get();
            Constants.KEY_PRESS_STATE.compareAndSet(currentValue, !currentValue);
        }
    }
    @PostConstruct
    public void init() {
        new Thread(() -> {
            try {
                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeKeyListener(this);
                System.out.println("Native hook registered successfully.");
            } catch (Exception e) {
                System.err.println("Error registering native hook: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
}