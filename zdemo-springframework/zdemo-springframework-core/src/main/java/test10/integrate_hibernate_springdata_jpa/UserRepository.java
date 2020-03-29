package test10.integrate_hibernate_springdata_jpa;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * UserRepository
 * @author zhangqingli
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>{

}
