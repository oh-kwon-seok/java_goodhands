package com.springboot.new_java.data.dto.user;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class Permission {
    private int read;
    private int create;
    private int update;

    private int delete;

    public Permission(int read, int create,int update, int delete) {
        this.read = read;
        this.create = create;
        this.update = update;
        this.delete = delete;
    }
}
