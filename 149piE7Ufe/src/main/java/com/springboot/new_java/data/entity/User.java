package com.springboot.new_java.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table


public class User implements UserDetails {

    @Id
    @Column(nullable = false, unique = true)
    private String id;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employment_uid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employment employment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_uid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Department department;

    @Column(nullable = false)

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String name;

    private String email;
    private String phone;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    @LastModifiedDate
    private LocalDateTime deleted;

    private Boolean used;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> auth = new ArrayList<>();


    @Column
    private String menu; // JSON 데이터를 저장하는 필드

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.auth.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.id;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 추가: JSON 직렬화/역직렬화 메서드
    public Map<String, Object> getMenuAsMap() {
        if (menu == null) {
            return new HashMap<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(menu, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public void setMenuFromMap(Map<String, Object> menuMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.menu = objectMapper.writeValueAsString(menuMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
