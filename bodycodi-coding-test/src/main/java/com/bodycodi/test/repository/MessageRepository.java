package com.bodycodi.test.repository;

import com.bodycodi.test.dto.ContentDto;
import com.bodycodi.test.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MessageRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int insert(MessageDto message) {
        int re = this.jdbcTemplate.update("insert into messages(id, sender, recipient, ttype, url, text, time values(?,?,?,?,?,?,?)",
            message.getId(), message.getSender(), message.getRecipient(), message.getContent().getType(), message.getContent().getUrl(), message.getContent().getText(), message.getTimestamp());
        return re;
    }


    public List<MessageDto> select(int recipient, int start, int limit) {
        return this.jdbcTemplate.query("select (id, sender, ttype, url, text, time) from messages where recipient = ? and to_char(time, 'YYYYMMDD') > ? limit ?", 
                new Integer[]{recipient, start, limit},
                (rs, rowNum) -> {
                    MessageDto dto = new MessageDto();
                    ContentDto cto = new ContentDto();
                    dto.setId(Integer.parseInt(rs.getString("id")));
                    dto.setId(Integer.parseInt(rs.getString("sender")));
                    cto.setType(rs.getString("ttype"));
                    cto.setText(rs.getString("text"));
                    cto.setUrl(rs.getString("url"));
                    dto.setContent(cto);
                    return dto;

        });
    }
}
