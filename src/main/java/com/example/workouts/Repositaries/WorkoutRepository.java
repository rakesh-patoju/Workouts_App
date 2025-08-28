package com.example.workouts.Repositaries;

import com.example.workouts.Entities.User;
import com.example.workouts.Entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUser(User user);
    List<Workout> findByDifficultyAndUser(String difficulty, User user);
}
