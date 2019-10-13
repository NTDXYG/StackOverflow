package com.example.stackoverflow.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Questions {
    String Id;
    String AcceptedAnswerId;
    String CreationDate;
    String Score;
    String ViewCount;
    String Body;
    String OwnerUserId;
    String LastEditorUserId;
    String LastEditorDisplayName;
    String LastEditDate;
    String LastActivityDate;
    String Title;
    String Tags;
    String AnswerCount;
    String CommentCount;
    String FavoriteCount;
    String CommunityOwnedDate;

}
