package com.example.javaserver.action.service;

import com.example.javaserver.action.controller.dto.ActionRequestDto;
import com.example.javaserver.action.controller.mapper.ActionMapper;
import com.example.javaserver.action.model.Action;
import com.example.javaserver.action.model.ActionType;
import com.example.javaserver.action.repo.ActionRepo;
import com.example.javaserver.action.repo.ActionTypeRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActionService {

    private final ActionRepo actionRepo;

    private final ActionTypeRepo actionTypeRepo;

    private final UserRepo userRepo;

    private final ActionMapper actionMapper;


    @Autowired
    public ActionService(ActionRepo actionRepo,
                         ActionTypeRepo actionTypeRepo,
                         UserRepo userRepo, ActionMapper actionMapper) {
        this.actionRepo = actionRepo;
        this.actionTypeRepo = actionTypeRepo;
        this.userRepo = userRepo;
        this.actionMapper = actionMapper;
    }

    public List<Action> getAll(){
        List<Action> all = (List<Action>) actionRepo.findAll();
        return all;
    }

    public Action createAction(ActionRequestDto dto){
        Action action = actionMapper.toEntity(dto);
        Optional<ActionType> byId = actionTypeRepo.findById(dto.getActionTypeId());
        action.setActionType(byId.get());
        return actionRepo.save(action);
    }

    public Action getById(Long id){
        Optional<Action> action = actionRepo.findById(id);
        return action.get();
    }

    public Action addUser(Long actionId, Integer Userid){
        Optional<User> byId = userRepo.findById(Userid);
        Optional<Action> byId1 = actionRepo.findById(actionId);
        Action action = byId1.get();
        action.getUsers().add(byId.get());
        return actionRepo.save(action);
    }

    public void delete(Long actionId){
        actionRepo.deleteById(actionId);
    }
}
