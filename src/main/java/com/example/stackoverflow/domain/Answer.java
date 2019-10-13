package com.example.stackoverflow.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {
    String Id;
    String ParentId;
    String CreationDate;
    String Score;
    String Body;
    String OwnerUserId;
    String LastEditorUserId;
    String LastEditorDisplayName;
    String LastEditDate;
    String LastActivityDate;
    String CommentCount;
    String CommunityOwnedDate;

}
