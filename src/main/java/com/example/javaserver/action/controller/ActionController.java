package com.example.javaserver.action.controller;

import com.example.javaserver.action.controller.dto.ActionRequestDto;
import com.example.javaserver.action.controller.dto.ActionResponseDto;
import com.example.javaserver.action.controller.mapper.ActionMapper;
import com.example.javaserver.action.model.Action;
import com.example.javaserver.action.model.ActionType;
import com.example.javaserver.action.service.ActionService;
import com.example.javaserver.action.service.ActionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionController {

    private final ActionService actionService;

    private final ActionTypeService actionTypeService;

    private final ActionMapper actionMapper;

    @Autowired
    public ActionController(ActionService actionService,
                            ActionTypeService actionTypeService,
                            ActionMapper actionMapper) {
        this.actionService = actionService;
        this.actionTypeService = actionTypeService;
        this.actionMapper = actionMapper;
    }

    @GetMapping("/all")
    public List<ActionResponseDto> getAllAction(){

        return null;
    }

    @GetMapping
    public ResponseEntity<ActionResponseDto> getActionById(@RequestParam Long id){
        Action action = actionService.getById(id);
        return ResponseEntity.ok(actionMapper.toDto(action));
    }

    @PostMapping
    public ResponseEntity<ActionResponseDto> createAction(@RequestBody ActionRequestDto dto){
        Action action = actionService.createAction(dto);
        return ResponseEntity.ok(actionMapper.toDto(action));
    }


    @PutMapping
    public ResponseEntity<ActionResponseDto> updateAction(@RequestBody ActionRequestDto dto){

        return null;
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteActionById(@RequestParam Long id){

        return ResponseEntity.ok(null);
    }

    @PostMapping("/users")
    public ResponseEntity<ActionResponseDto> createTypeAction(@RequestParam @NotNull Long actionId,
                                                       @RequestParam @NotNull Integer userId){
        Action action = actionService.addUser(actionId, userId);
        return ResponseEntity.ok(actionMapper.toDto(action));
    }

    @PostMapping("/type")
    public ResponseEntity<ActionType> createTypeAction(@RequestParam @NotNull String type){
        ActionType actionType = actionTypeService.createActionType(type);
        return ResponseEntity.ok(actionType);
    }

    @GetMapping("/type/all")
    public ResponseEntity<List<ActionType>> getAll(){
        return ResponseEntity.ok(actionTypeService.getAll());
    }
}
