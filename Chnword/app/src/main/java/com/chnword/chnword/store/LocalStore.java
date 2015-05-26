package com.chnword.chnword.store;

import android.content.Context;
import android.content.SharedPreferences;

import com.chnword.chnword.beans.Module;
import com.chnword.chnword.beans.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by khtc on 15/5/26.
 */
public class LocalStore {

    private static final String USERNAMEKEY = "USERNAMEKEY";

    private static final String WORDDEFAULTKEY = "WORDDEFAULTKEY";

    private SharedPreferences perference;


    public LocalStore(Context context) {
        perference = context.getSharedPreferences("LocalStore", Context.MODE_PRIVATE);

    }


    public void addUser(String userCode) {
        Set<String> set = new HashSet<String>();

        Set<String> users = perference.getStringSet(USERNAMEKEY, set);
        users.add(userCode);

        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(WORDDEFAULTKEY, users);
        editor.commit();

    }

    public boolean isContainUser(String userCode) {
        boolean result = false;
        Set<String> names = perference.getStringSet(USERNAMEKEY, null);

        if (names != null || names.contains(userCode)) {
            return true;
        }

        return result;
    }

    //-------------------default module and word

    public void setDefaultModule(List<Module> list) {
        Set<String> set = new HashSet<String>();

        for (Module module : list ) {
            set.add(module.getName() + ":" + module.getCname());
        }
        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(WORDDEFAULTKEY, set);
        editor.commit();

    }

    public List<Module> getDefaultModule() {
        Set<String> set = new HashSet<String>();
        Set<String> modules = perference.getStringSet(WORDDEFAULTKEY, set);
        List<Module> lists = new ArrayList<Module>();
        for (String str : modules) {
            String[] strs = str.split(":");
            Module m = new Module();
            m.setName(strs[0]);
            m.setCname(strs[1]);
        }
        return lists;
    }

    public void setDefaultWord(String moduleName, List<Word> words) {
        Set<String> set = new HashSet<String>();

        for (Word word : words) {
            set.add(word.getWord() + ":" + word.getWord());
        }

        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(moduleName + "_WordDefault", set);
        editor.commit();
    }

    public List<Word> getDefaultWord(String moduleName) {
        Set<String> set = new HashSet<String>();
        Set<String> modules = perference.getStringSet(moduleName + "_WordDefault", set);
        List<Word> lists = new ArrayList<Word>();
        for (String str : modules) {
            String[] strs = str.split(":");
            Word m = new Word();
            m.setWord(strs[0]);
            m.setWordIndex(strs[1]);
        }
        return lists;
    }


    //--------------------users


    public void setDefaultModule(List<Module> list, String userCode) {
        Set<String> set = new HashSet<String>();

        for (Module module : list ) {
            set.add(module.getName() + ":" + module.getCname());
        }

        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(userCode + "_defaultModule", set);
        editor.commit();

    }

    public List<Module> getDefaultModule(String userCode) {
        Set<String> set = new HashSet<String>();
        Set<String> modules = perference.getStringSet(userCode + "_defaultModule", set);
        List<Module> lists = new ArrayList<Module>();
        for (String str : modules) {
            String[] strs = str.split(":");
            Module m = new Module();
            m.setName(strs[0]);
            m.setCname(strs[1]);
        }
        return lists;
    }

    public void setDefaultWord(String userCode, String moduleName, List<Word> words) {
        Set<String> set = new HashSet<String>();

        for (Word word : words) {
            set.add(word.getWord() + ":" + word.getWord());
        }
        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(userCode + "_" + moduleName + "_WordDefault", set);
        editor.commit();
    }

    public List<Word> getDefaultWord(String userCode, String moduleName) {
        Set<String> set = new HashSet<String>();
        Set<String> modules = perference.getStringSet(userCode + "_" + moduleName + "_WordDefault", set);
        List<Word> lists = new ArrayList<Word>();
        for (String str : modules) {
            String[] strs = str.split(":");
            Word m = new Word();
            m.setWord(strs[0]);
            m.setWordIndex(strs[1]);
        }
        return lists;
    }

}
