package com.gramazski.craps.command;

import com.gramazski.craps.command.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gs on 22.02.2017.
 */
public class CommandManager {
    private Map<String, ICommand> commandMap;
    private static CommandManager instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean instanceFlag = new AtomicBoolean(false);

    private CommandManager(){
        commandMap = new HashMap<String, ICommand>();
        commandMap.put("LOGIN", new LoginCommand());
        commandMap.put("LOGOUT", new LogoutCommand());
        commandMap.put("REGISTER", new RegisterCommand());
        commandMap.put("RELOAD", new ReloadCommand());
        commandMap.put("UPDATE", new UpdateUserCommand());
        commandMap.put("TRANSFER", new TransferCommand());
        commandMap.put("LOADUSERS", new GetUsersCommand());
        commandMap.put("BAN", new BanUserCommand());
        commandMap.put("UNBAN", new UnBanUserCommand());
        commandMap.put("LOADGAMES", new LoadGamesCommand());
        commandMap.put("ADDGAME", new AddGameCommand());
        commandMap.put("REMOVEGAME", new RemoveGameCommand());
        commandMap.put("LOADGAME", new LoadGameCommand());
        commandMap.put("THROWCUBE", new ThrowCubeCommand());
        commandMap.put("BECOMETHROWER", new BecomeThrowerCommand());
        commandMap.put("PLAYGAME", new PlayGameCommand());
        commandMap.put("LEAVEGAME", new LeaveGameCommand());
    }

    /**
     * @return
     */
    public static CommandManager getInstance(){
        if (!instanceFlag.get()){
            lock.lock();
            try {
                if (!instanceFlag.get()){
                    instance = new CommandManager();
                    instanceFlag.getAndSet(true);
                }
            }
            finally {
                lock.unlock();
            }
        }

        return instance;
    }

    /**
     * @param key
     * @param command
     * @return
     */
    public boolean registerCommand(String key, ICommand command){
        lock.lock();
        try {
            if ((key == null) || commandMap.containsKey(key) || (command == null)){
                return false;
            }

            commandMap.put(key, command);
            return true;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * @param key
     * @return
     */
    public ICommand getCommand(String key){
        if (commandMap.containsKey(key)){
            return commandMap.get(key);
        }

        return null;
    }
}
