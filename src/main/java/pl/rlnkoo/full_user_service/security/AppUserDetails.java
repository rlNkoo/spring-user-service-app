package pl.rlnkoo.full_user_service.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.rlnkoo.full_user_service.user.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AppUserDetails implements UserDetails {

    private String userName;

    private String password;

    private boolean isEnabled;

    private List<GrantedAuthority> authorities;

    public AppUserDetails(User user) {

        this.userName = user.getEmail();

        this.password = user.getPassword();

        this.isEnabled = user.isEnabled();

        this.authorities = Arrays.stream(user.getRoles().toString().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
