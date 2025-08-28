package com.example.workouts.Controllers;

import com.example.workouts.Entities.User;
import com.example.workouts.Entities.Workout;
import com.example.workouts.Services.UserService;
import com.example.workouts.Services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class UserWorkoutController {

    @Autowired
    private UserService userService;
    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/")
    public String getHome(){
        return "home";
    }

    @GetMapping("register")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            if (userService.usernameExists(user.getUsername())) {
                throw new RuntimeException("Username Already Exists");
            }
            if (userService.emailExists(user.getEmail())) {
                throw new RuntimeException("Email Already Exists");
            }

            userService.registration(user);
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user); // keep entered values
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "login";
    }

        @GetMapping("/dashboard")
        public String showDashboard(Model model) {
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                // Check authentication
                if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
                    return "redirect:/login";
                }

                String username = auth.getName();

                // Find user
                Optional<User> userOptional = userService.findByUsername(username);
                if (userOptional.isEmpty()) {
                    return "redirect:/login?error=true";
                }

                User user = userOptional.get();

                // Fetch user workouts
                List<Workout> workouts = workoutService.getUserWorkouts(user);
                if (workouts == null) {
                    workouts = Collections.emptyList();
                }

                // Add attributes for Thymeleaf
                model.addAttribute("username", username);
                model.addAttribute("workouts", workouts);
                model.addAttribute("workout", new Workout()); // For add workout form

                return "dashboard"; // Thymeleaf template name

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/login?error=true";
            }
        }


    @PostMapping("/addWorkout")
    public String addWorkout(@ModelAttribute Workout workout) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent()) {
            workout.setUser(user.get());
            workoutService.saveWorkout(workout);
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/deleteWorkout/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "redirect:/dashboard";
    }


}
