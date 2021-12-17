package com.example.javaserver.action.service;

import com.example.javaserver.action.model.ActionType;
import com.example.javaserver.action.repo.ActionTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActionTypeService {

    private final ActionTypeRepo actionTypeRepo;

    @Autowired
    public ActionTypeService(ActionTypeRepo actionTypeRepo) {
        this.actionTypeRepo = actionTypeRepo;
    }

    public ActionType createActionType(String type){
        ActionType actionType = new ActionType();
        actionType.setType(type);
        return actionTypeRepo.save(actionType);
    }

    public List<ActionType> getAll(){
        return (List<ActionType>) actionTypeRepo.findAll();
    }


}

