package com.example.board_game.domain.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MatchingQueue {
    private final List<Vector<Long>> queue;     // userId(Long) 큐
    private final int capacity;                 // 첫번째 조건 : 인원수
    private final int[] conditions;             // 부수적인 조건들

    public MatchingQueue(int capacity, int... condition) {
        this.capacity = capacity;
        this.conditions = condition;
        int multi = (capacity+1);
        for (int cnt : condition) {
            multi *= cnt;
        }
        this.queue = new ArrayList<>(multi);
        for (int i=0; i<multi; i++) {
            queue.add(new Vector<>());
        }
    }

    public List<Long> enQueue(Long userId, int capacity, int... condition) {
        checkCondition(capacity, condition);

        int index = findIndex(capacity, condition);

        queue.get(index).add(userId);

        List<Long> result = null;
        if(queue.get(index).size() == capacity) {
            result = new ArrayList<>(queue.get(index));
            queue.get(index).clear();
        }

        return result;
    }

    public void delete(Long userId) {
        for (Vector<Long> q : queue) {
            q.remove(userId);
        }
    }

    // 조건으로부터 index 를 찾는 메서드
    private int findIndex(int capacity, int[] condition) {
        int result = 0;

        int tmp = 1;
        for(int i=0; i<conditions.length; i++) {
            tmp *= conditions[i];
        }
        result += capacity * tmp;

        for(int i = 0; i< condition.length; i++) {
            tmp = 1;
            for(int j = i+1; j< conditions.length; j++) {
                tmp *= conditions[j];
            }
            result += condition[i] * tmp;
        }
        return result;
    }

    // 조건을 확인하는 메서드
    private void checkCondition(int capacity, int[] condition) {
        if(this.capacity < capacity) {
            throw new IllegalArgumentException("조건의 인원수를 초과합니다.");
        }

        if(conditions.length != condition.length) {
            throw new IllegalArgumentException("조건의 개수가 다릅니다.");
        }

        for(int i=0; i<conditions.length; i++) {
            if(conditions[i] <= condition[i]) {
                throw new IllegalArgumentException("조건의 범위를 벗어납니다.");
            }
        }
    }
}
