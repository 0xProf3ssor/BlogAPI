package com.zenith.blog.request;

import com.zenith.blog.validator.EmailNotExists;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRequest{
        @NotEmpty(message = "Name is required")
        @Size(min = 3, max = 100, message = "Name length must be between 3-12 characters")
        private String name;
        @NotEmpty(message = "Email is required")
        @Email(message = "Email is not valid")
        @EmailNotExists
        private String email;
        @NotEmpty(message = "Password is required")
        @Size(min = 6, max = 40, message = "Password length must be between 6-40 characters")
        private String password;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name.trim();
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email.trim();
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password.trim();
        }
}