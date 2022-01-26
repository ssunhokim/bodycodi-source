package com.bodycodi.test.controller;

import com.bodycodi.test.dto.MessageDto;
import com.bodycodi.test.dto.UserDto;
import com.bodycodi.test.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 메세지를 입력한다.
     * @param userDto
     * @param messageDto
     * @return
     */
    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> insert(@RequestAttribute("user") UserDto userDto, @RequestBody MessageDto messageDto) {
        if(StringUtils.isEmpty(messageDto.getSender()) || StringUtils.isEmpty(messageDto.getRecipient()) || messageDto.getContent() == null) {
            throw new RuntimeException("Sender, recepient, and content required");
        }

        String id = "";

        Map<String,Object> re = new HashMap<>();
        re.put("id", id);
        re.put("timestamp", new Date());

        messageService.insert(messageDto);

        return ResponseEntity.status(HttpStatus.OK).body(re);
    }

    /**
     * 메세지 리스트를 가지고 온다.
     * @param recipient
     * @param start
     * @param limit
     * @return
     */
    @GetMapping("/messages")
    public ResponseEntity<Map<String,List<MessageDto>>> getMessages(@RequestParam int recipient, @RequestParam int start, @RequestParam(required = false, defaultValue = "100") int limit) {
        List<MessageDto> messageDtos = new ArrayList<>(); //메세지를 가지고 오는 구조 개발
        Map<String,List<MessageDto>> re = new HashMap<>();
        re.put("messages", messageDtos);
        return ResponseEntity.status(HttpStatus.OK).body(re);
    }

}
