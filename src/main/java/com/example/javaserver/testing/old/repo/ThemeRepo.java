//package com.example.javaserver.testing.repo;
//
//import com.example.javaserver.testing.model.Theme;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface ThemeRepo extends JpaRepository<Theme, Long> {
//    List<Theme> findAllBySubjectId(Long subjectId);
//    boolean existsByName(String name);
//    @Query(value = "select distinct theme_id from (select * from passed_tests where user_id = ?1) as \"user\"\n" +
//            " join (select * from themes where subject_id = ?2) as subj on \"user\".theme_id = subj.id", nativeQuery = true)
//    List<Long> fetchPassedThemeIdsByUserId(Integer id, Long subjId);
//
//}
