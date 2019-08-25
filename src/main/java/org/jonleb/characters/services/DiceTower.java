package org.jonleb.characters.services;

import lombok.extern.log4j.Log4j2;
import org.jonleb.characters.constraints.DiceTowerConstraint;
import org.jonleb.characters.constraints.RollConstraint;
import org.jonleb.characters.utils.DiceRoller;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@Validated
public class DiceTower {

    @RollConstraint(rollTypes = {RollType.TOTAL, RollType.TOTAL_BY_TYPE})
    public Map<String, Integer> roll(
            @Valid RollType rollType,
            @DiceTowerConstraint String ... args){
        Map result = null;

        switch (rollType) {
            case TOTAL:
                result = rollForTotal(args);
                break;
            case TOTAL_BY_TYPE:
                result = rollForTotalBySide(args);
                break;
        }
        return result;
    }

    @RollConstraint(rollTypes = {RollType.SUCCESS, RollType.SUCCESS_BY_TYPE})
    public Map<String, Integer> rollForSuccess(
            @Valid RollType rollType,
            int limitSuccess,
            @DiceTowerConstraint String ... args){

        Map result = null;

        switch (rollType) {
            case SUCCESS:
                result = rollForSuccess(limitSuccess,args);
                break;
            case SUCCESS_BY_TYPE:
                result = rollForSuccessBySide(limitSuccess, args);
                break;
        }
        return result;
    }

    private Map rollForTotal(String ... args){
        Map result = new HashMap(args.length);
        int total = 0;
        String key = "";
        DiceRoller diceRoller = null;
        String [] n;
        for (String arg : args) {
            n = arg.split("[D|d]");
            diceRoller = new DiceRoller(Integer.valueOf(n[0]), Integer.valueOf(n[1]));
            total += diceRoller.roll();
            key += arg + " ";
        }
        result.put(key.trim(),total);
        return result;
    }

    private Map rollForTotalBySide(String ... args){
        Map result = new HashMap(args.length);
        DiceRoller diceRoller = null;
        String [] n;
        for (String arg : args) {
            n = arg.split("[D|d]");
            diceRoller = new DiceRoller(Integer.valueOf(n[0]), Integer.valueOf(n[1]));
            result.put(arg,diceRoller.roll());
        }
        return result;
    }

    private Map rollForSuccess(int limitSuccess, String ... args){
        Map result = new HashMap(args.length);
        DiceRoller diceRoller = null;
        String key = "";
        int successNumber = 0;
        String [] n;
        int loops = 0;
        for(String arg: args){
            n =arg.split("[D|d]");
            loops = Integer.valueOf(n[0]);
            for (int i =0; i < loops; i++){
                diceRoller = new DiceRoller(1, Integer.valueOf(n[1]));
                if (diceRoller.roll() >= limitSuccess){
                    successNumber ++;
                };
            }
            key += arg + " ";
        }
        result.put(key.trim(),successNumber);
        return result;
    }

    private Map rollForSuccessBySide(int limitSuccess, String ... args){

        Map result = new HashMap(args.length);
        DiceRoller diceRoller = null;
        String key = "";
        int successNumber = 0;
        String [] n;
        int loops = 0;
        for(String arg: args){
            n = arg.split("[D|d]");
            loops = Integer.valueOf(n[0]);
            for (int i =0; i < loops; i++){
                diceRoller = new DiceRoller(1, Integer.valueOf(n[1]));
                if (diceRoller.roll() >= limitSuccess){
                    successNumber ++;
                };
            }
            result.put(key.trim(),successNumber);
            successNumber = 0;
        }

        return result;
    }


}
