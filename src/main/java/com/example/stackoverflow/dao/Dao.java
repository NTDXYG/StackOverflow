package com.example.stackoverflow.dao;

import com.example.stackoverflow.domain.Answer;
import com.example.stackoverflow.domain.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Dao {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public void parseJavascript(String filePath) throws FileNotFoundException, XMLStreamException {
        // 解析xml获得数据
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        inputFactory.setProperty("http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo", "yes");
        // 设置entity size , 否则会报 JAXP00010004 错误
        inputFactory.setProperty("http://www.oracle.com/xml/jaxp/properties/totalEntitySizeLimit", Integer.MAX_VALUE);
        File file = new File(filePath);
        InputStream isS= new FileInputStream(file);
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(isS);
        List<Questions> questions = new ArrayList<>();
        while (streamReader.hasNext()) {
            streamReader.next();
            if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                if (streamReader.getLocalName().equals("row")) {
                    String postTypeId = streamReader.getAttributeValue(null, "PostTypeId");
                    // 1 Question, 2 Answer
                    if ("1".equals(postTypeId)) {
                        String Id = streamReader.getAttributeValue(null, "Id");
                        String AcceptedAnswerId = streamReader.getAttributeValue(null, "AcceptedAnswerId");
                        String CreationDate = streamReader.getAttributeValue(null, "CreationDate");
                        String Score = streamReader.getAttributeValue(null, "Score");
                        String ViewCount = streamReader.getAttributeValue(null, "ViewCount");
                        String Body = streamReader.getAttributeValue(null, "Body");
                        String OwnerUserId = streamReader.getAttributeValue(null, "OwnerUserId");
                        String LastEditorUserId = streamReader.getAttributeValue(null, "LastEditorUserId");
                        String LastEditorDisplayName = streamReader.getAttributeValue(null, "LastEditorDisplayName");
                        String LastEditDate = streamReader.getAttributeValue(null, "LastEditDate");
                        String LastActivityDate = streamReader.getAttributeValue(null, "LastActivityDate");
                        String Title = streamReader.getAttributeValue(null, "Title");
                        String Tags = streamReader.getAttributeValue(null, "Tags");
                        String AnswerCount = streamReader.getAttributeValue(null, "AnswerCount");
                        String CommentCount = streamReader.getAttributeValue(null, "CommentCount");
                        String FavoriteCount = streamReader.getAttributeValue(null, "FavoriteCount");
                        String CommunityOwnedDate = streamReader.getAttributeValue(null, "CommunityOwnedDate");
                        Questions question = new Questions(Id, AcceptedAnswerId, CreationDate, Score, ViewCount, Body, OwnerUserId, LastEditorUserId,
                                LastEditorDisplayName, LastEditDate, LastActivityDate, Title, Tags, AnswerCount, CommentCount, FavoriteCount, CommunityOwnedDate);
                        if (Tags.contains("javascript")) {
                            questions.add(question);
                        }
                        if (questions.size() == 100) {
                            jdbcTemplate.batchUpdate("insert into javascript values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                    new BatchPreparedStatementSetter() {
                                        @Override
                                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                                            ps.setString(1, questions.get(i).getId());
                                            ps.setString(2, questions.get(i).getAcceptedAnswerId());
                                            ps.setString(3, questions.get(i).getCreationDate());
                                            ps.setString(4, questions.get(i).getScore());
                                            ps.setString(5, questions.get(i).getViewCount());
                                            ps.setString(6, questions.get(i).getBody());
                                            ps.setString(7, questions.get(i).getOwnerUserId());
                                            ps.setString(8, questions.get(i).getLastEditorUserId());
                                            ps.setString(9, questions.get(i).getLastEditorDisplayName());
                                            ps.setString(10, questions.get(i).getLastEditDate());
                                            ps.setString(11, questions.get(i).getLastActivityDate());
                                            ps.setString(12, questions.get(i).getTitle());
                                            ps.setString(13, questions.get(i).getTags());
                                            ps.setString(14, questions.get(i).getAnswerCount());
                                            ps.setString(15, questions.get(i).getCommentCount());
                                            ps.setString(16, questions.get(i).getFavoriteCount());
                                            ps.setString(17, questions.get(i).getCommunityOwnedDate());
                                        }

                                        @Override
                                        public int getBatchSize() {
                                            return questions.size();
                                        }
                                    });
                            questions.clear();
                        }
                    }
                }
            }
        }
        jdbcTemplate.batchUpdate("insert into javascript values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter(){
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, questions.get(i).getId());
                        ps.setString(2, questions.get(i).getAcceptedAnswerId());
                        ps.setString(3, questions.get(i).getCreationDate());
                        ps.setString(4, questions.get(i).getScore());
                        ps.setString(5, questions.get(i).getViewCount());
                        ps.setString(6, questions.get(i).getBody());
                        ps.setString(7, questions.get(i).getOwnerUserId());
                        ps.setString(8, questions.get(i).getLastEditorUserId());
                        ps.setString(9, questions.get(i).getLastEditorDisplayName());
                        ps.setString(10, questions.get(i).getLastEditDate());
                        ps.setString(11, questions.get(i).getLastActivityDate());
                        ps.setString(12, questions.get(i).getTitle());
                        ps.setString(13, questions.get(i).getTags());
                        ps.setString(14, questions.get(i).getAnswerCount());
                        ps.setString(15, questions.get(i).getCommentCount());
                        ps.setString(16, questions.get(i).getFavoriteCount());
                        ps.setString(17, questions.get(i).getCommunityOwnedDate());
                    }
                    @Override
                    public int getBatchSize() {
                        return questions.size();
                    }
                } );

    }

    public void parsePosts(String filePath) throws FileNotFoundException, XMLStreamException {

        if (jdbcTemplate==null){
            System.out.println("null");
        }
        // 解析xml获得数据
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        inputFactory.setProperty("http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo", "yes");
        // 设置entity size , 否则会报 JAXP00010004 错误
        inputFactory.setProperty("http://www.oracle.com/xml/jaxp/properties/totalEntitySizeLimit", Integer.MAX_VALUE);
        File file = new File(filePath);
        InputStream isS= new FileInputStream(file);
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(isS);
        List<Questions> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        while (streamReader.hasNext()) {
            streamReader.next();
            if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                if (streamReader.getLocalName().equals("row")) {
                    String postTypeId = streamReader.getAttributeValue(null, "PostTypeId");
                    // 1 Question, 2 Answer
                    if ("1".equals(postTypeId)) {
                        String Id = streamReader.getAttributeValue(null, "Id");
                        String AcceptedAnswerId = streamReader.getAttributeValue(null, "AcceptedAnswerId");
                        String CreationDate = streamReader.getAttributeValue(null, "CreationDate");
                        String Score = streamReader.getAttributeValue(null, "Score");
                        String ViewCount = streamReader.getAttributeValue(null, "ViewCount");
                        String Body = streamReader.getAttributeValue(null, "Body");
                        String OwnerUserId = streamReader.getAttributeValue(null, "OwnerUserId");
                        String LastEditorUserId = streamReader.getAttributeValue(null, "LastEditorUserId");
                        String LastEditorDisplayName = streamReader.getAttributeValue(null, "LastEditorDisplayName");
                        String LastEditDate = streamReader.getAttributeValue(null, "LastEditDate");
                        String LastActivityDate = streamReader.getAttributeValue(null, "LastActivityDate");
                        String Title = streamReader.getAttributeValue(null, "Title");
                        String Tags = streamReader.getAttributeValue(null, "Tags");
                        String AnswerCount = streamReader.getAttributeValue(null, "AnswerCount");
                        String CommentCount = streamReader.getAttributeValue(null, "CommentCount");
                        String FavoriteCount = streamReader.getAttributeValue(null, "FavoriteCount");
                        String CommunityOwnedDate = streamReader.getAttributeValue(null, "CommunityOwnedDate");
                        Questions question = new Questions(Id, AcceptedAnswerId, CreationDate, Score, ViewCount, Body, OwnerUserId, LastEditorUserId,
                                LastEditorDisplayName, LastEditDate,LastActivityDate,Title,Tags,AnswerCount,CommentCount,FavoriteCount, CommunityOwnedDate);
                        questions.add(question);
                        if (questions.size() == 100){
                            jdbcTemplate.batchUpdate("insert into questions values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                    new BatchPreparedStatementSetter(){
                                        @Override
                                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                                            ps.setString(1, questions.get(i).getId());
                                            ps.setString(2, questions.get(i).getAcceptedAnswerId());
                                            ps.setString(3, questions.get(i).getCreationDate());
                                            ps.setString(4, questions.get(i).getScore());
                                            ps.setString(5, questions.get(i).getViewCount());
                                            ps.setString(6, questions.get(i).getBody());
                                            ps.setString(7, questions.get(i).getOwnerUserId());
                                            ps.setString(8, questions.get(i).getLastEditorUserId());
                                            ps.setString(9, questions.get(i).getLastEditorDisplayName());
                                            ps.setString(10, questions.get(i).getLastEditDate());
                                            ps.setString(11, questions.get(i).getLastActivityDate());
                                            ps.setString(12, questions.get(i).getTitle());
                                            ps.setString(13, questions.get(i).getTags());
                                            ps.setString(14, questions.get(i).getAnswerCount());
                                            ps.setString(15, questions.get(i).getCommentCount());
                                            ps.setString(16, questions.get(i).getFavoriteCount());
                                            ps.setString(17, questions.get(i).getCommunityOwnedDate());
                                        }
                                        @Override
                                        public int getBatchSize() {
                                            return questions.size();
                                        }
                                    } );
                            questions.clear();
                        }
                    } else {
                        String Id = streamReader.getAttributeValue(null, "Id");
                        String ParentId = streamReader.getAttributeValue(null, "ParentId");
                        String CreationDate = streamReader.getAttributeValue(null, "CreationDate");
                        String Score = streamReader.getAttributeValue(null, "Score");
                        String Body = streamReader.getAttributeValue(null, "Body");
                        String OwnerUserId = streamReader.getAttributeValue(null, "OwnerUserId");
                        String LastEditorUserId = streamReader.getAttributeValue(null, "LastEditorUserId");
                        String LastEditorDisplayName = streamReader.getAttributeValue(null, "LastEditorDisplayName");
                        String LastEditDate = streamReader.getAttributeValue(null, "LastEditDate");
                        String LastActivityDate = streamReader.getAttributeValue(null, "LastActivityDate");
                        String CommentCount = streamReader.getAttributeValue(null, "CommentCount");
                        String CommunityOwnedDate = streamReader.getAttributeValue(null, "CommunityOwnedDate");
                        Answer answer = new Answer(Id, ParentId, CreationDate, Score, Body, OwnerUserId, LastEditorUserId,
                                LastEditorDisplayName, LastEditDate,LastActivityDate,CommentCount,CommunityOwnedDate);
                        answers.add(answer);
                        if (answers.size() == 100){
                            jdbcTemplate.batchUpdate("insert into answer values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                    new BatchPreparedStatementSetter(){
                                        @Override
                                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                                            ps.setString(1, answers.get(i).getId());
                                            ps.setString(2, answers.get(i).getParentId());
                                            ps.setString(3, answers.get(i).getCreationDate());
                                            ps.setString(4, answers.get(i).getScore());
                                            ps.setString(5, answers.get(i).getBody());
                                            ps.setString(6, answers.get(i).getOwnerUserId());
                                            ps.setString(7, answers.get(i).getLastEditorUserId());
                                            ps.setString(8, answers.get(i).getLastEditorDisplayName());
                                            ps.setString(9, answers.get(i).getLastEditDate());
                                            ps.setString(10, answers.get(i).getLastActivityDate());
                                            ps.setString(11, answers.get(i).getCommentCount());
                                            ps.setString(12, answers.get(i).getCommunityOwnedDate());
                                        }
                                        @Override
                                        public int getBatchSize() {
                                            return questions.size();
                                        }
                                    } );
                            answers.clear();
                        }
                    }
                }
            }
        }

        jdbcTemplate.batchUpdate("insert into questions values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter(){
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, questions.get(i).getId());
                        ps.setString(2, questions.get(i).getAcceptedAnswerId());
                        ps.setString(3, questions.get(i).getCreationDate());
                        ps.setString(4, questions.get(i).getScore());
                        ps.setString(5, questions.get(i).getViewCount());
                        ps.setString(6, questions.get(i).getBody());
                        ps.setString(7, questions.get(i).getOwnerUserId());
                        ps.setString(8, questions.get(i).getLastEditorUserId());
                        ps.setString(9, questions.get(i).getLastEditorDisplayName());
                        ps.setString(10, questions.get(i).getLastEditDate());
                        ps.setString(11, questions.get(i).getLastActivityDate());
                        ps.setString(12, questions.get(i).getTitle());
                        ps.setString(13, questions.get(i).getTags());
                        ps.setString(14, questions.get(i).getAnswerCount());
                        ps.setString(15, questions.get(i).getCommentCount());
                        ps.setString(16, questions.get(i).getFavoriteCount());
                        ps.setString(17, questions.get(i).getCommunityOwnedDate());
                    }
                    @Override
                    public int getBatchSize() {
                        return questions.size();
                    }
                } );

        jdbcTemplate.batchUpdate("insert into answer values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter(){
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, answers.get(i).getId());
                        ps.setString(2, answers.get(i).getParentId());
                        ps.setString(3, answers.get(i).getCreationDate());
                        ps.setString(4, answers.get(i).getScore());
                        ps.setString(5, answers.get(i).getBody());
                        ps.setString(6, answers.get(i).getOwnerUserId());
                        ps.setString(7, answers.get(i).getLastEditorUserId());
                        ps.setString(8, answers.get(i).getLastEditorDisplayName());
                        ps.setString(9, answers.get(i).getLastEditDate());
                        ps.setString(10, answers.get(i).getLastActivityDate());
                        ps.setString(11, answers.get(i).getCommentCount());
                        ps.setString(12, answers.get(i).getCommunityOwnedDate());
                    }
                    @Override
                    public int getBatchSize() {
                        return questions.size();
                    }
                } );
    }
}
