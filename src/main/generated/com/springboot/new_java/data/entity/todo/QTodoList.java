package com.springboot.new_java.data.entity.todo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTodoList is a Querydsl query type for TodoList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTodoList extends EntityPathBase<TodoList> {

    private static final long serialVersionUID = 1540154778L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTodoList todoList = new QTodoList("todoList");

    public final com.springboot.new_java.data.entity.QBaseEntity _super = new com.springboot.new_java.data.entity.QBaseEntity(this);

    public final com.springboot.new_java.data.entity.care.QCareSchedule caregiverSchedule;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath todo_list = createString("todo_list");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final com.springboot.new_java.data.entity.QUser user;

    public QTodoList(String variable) {
        this(TodoList.class, forVariable(variable), INITS);
    }

    public QTodoList(Path<? extends TodoList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTodoList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTodoList(PathMetadata metadata, PathInits inits) {
        this(TodoList.class, metadata, inits);
    }

    public QTodoList(Class<? extends TodoList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.caregiverSchedule = inits.isInitialized("caregiverSchedule") ? new com.springboot.new_java.data.entity.care.QCareSchedule(forProperty("caregiverSchedule"), inits.get("caregiverSchedule")) : null;
        this.user = inits.isInitialized("user") ? new com.springboot.new_java.data.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

