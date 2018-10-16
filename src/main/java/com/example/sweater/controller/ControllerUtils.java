package com.example.sweater.controller;

import com.example.sweater.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {


    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    static Set<User> getFiveUser(Set<User> users) {
        Set<User> set = new HashSet<>();
        if (users.size() >= 5) {
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()){
                if (set.size() == 5){
                    return set;
                }
                set.add(iterator.next());
            }
        }
        return users;
    }

    static String buildParams(Pageable pageable) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("?page=");
        buffer.append(String.valueOf(pageable.getPageNumber()));
        buffer.append("&size=");
        buffer.append(String.valueOf(pageable.getPageSize()));
        return buffer.toString();
    }
}
