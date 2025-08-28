package com.example.workouts.Services;

import com.example.workouts.Entities.User;
import com.example.workouts.Entities.Workout;
import com.example.workouts.Repositaries.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    public Workout saveWorkout(Workout workout){
        return workoutRepository.save(workout);
    }

    public List<Workout> getUserWorkouts(User user){
        return workoutRepository.findByUser(user);
    }

    public List<Workout> getWorkoutDiffculty(String diff_level, User user){
        return workoutRepository.findByDifficultyAndUser(diff_level, user);
    }

    public void deleteWorkout(Long id){
         workoutRepository.deleteById(id);
    }

    public Optional<Workout> getWorkoutById(Long id){
        return workoutRepository.findById(id);
    }
}
