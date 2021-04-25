package com.example.javaserver.testing.n.service;

import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.n.config.QuestionType;
import com.example.javaserver.testing.n.dto.answer.for_test.checking.CheckTestDto;
import com.example.javaserver.testing.n.dto.answer.for_test.result.AfterCheckQuestionDto;
import com.example.javaserver.testing.n.dto.answer.for_test.result.AfterCheckTestDto;
import com.example.javaserver.testing.n.dto.answer.for_test.result.UserSelectableAnswer;
import com.example.javaserver.testing.n.dto.answer.for_test.result.UserWriteableAnswer;
import com.example.javaserver.testing.n.dto.mapper.CustomQuestionMapper;
import com.example.javaserver.testing.n.dto.question.QuestionDataDto;
import com.example.javaserver.testing.n.model.MatchableQuestion;
import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.model.SelectableQuestion;
import com.example.javaserver.testing.n.model.WriteableQuestion;
import com.example.javaserver.testing.n.model.answer.AnswerOption;
import com.example.javaserver.testing.n.model.answer.MatchableAnswerOption;
import com.example.javaserver.testing.n.model.answer.WriteableAnswerOption;
import com.example.javaserver.testing.n.model.saving_result.PassedTest;
import com.example.javaserver.testing.n.model.saving_result.answer.PassedSelectableAnswer;
import com.example.javaserver.testing.n.model.saving_result.answer.PassedWriteableAnswer;
import com.example.javaserver.testing.n.model.saving_result.question.PassedQuestionData;
import com.example.javaserver.testing.n.model.saving_result.question.PassedSelectableQuestion;
import com.example.javaserver.testing.n.model.saving_result.question.PassedWriteableQuestion;
import com.example.javaserver.testing.n.repo.PassedTestRepo;
import com.example.javaserver.testing.n.repo.QuestionRepo;
import com.example.javaserver.testing.n.service.model.CheckQuestion;
import com.example.javaserver.testing.n.service.model.ResultOfSomethingChecking;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.user.repo.UserRepo;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestService {
    private final PassedTestRepo passedTestRepo;
    private final CustomQuestionMapper customQuestionMapper;
    private final UserRepo userRepo;
    private final ThemeRepo themeRepo;
    private final QuestionRepo questionRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    public TestService(PassedTestRepo passedTestRepo, CustomQuestionMapper customQuestionMapper, UserRepo userRepo, ThemeRepo themeRepo, QuestionRepo questionRepo) {
        this.passedTestRepo = passedTestRepo;
        this.customQuestionMapper = customQuestionMapper;
        this.userRepo = userRepo;
        this.themeRepo = themeRepo;
        this.questionRepo = questionRepo;
    }

    public Set<QuestionDataDto> createTest(Long themeId, Integer countOfQuestions) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new Theme(themeId), themeRepo, checkResult);
        if (!checkResult.getItsOK())
            throw checkResult.getResponseStatusException();
        if (countOfQuestions == null) {
            countOfQuestions = checkResult.getTheme().getQuestionQuantityInTest();
            if (countOfQuestions == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Уточните количество вопросов в запросе или в БД.");
        }
        if (countOfQuestions < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Количество вопросов не может быть меньше 1.");
        Set<QuestionData> questions4Test = questionRepo.findAllByThemeId(themeId);
        countOfQuestions = Math.min(countOfQuestions, questions4Test.size());
        questions4Test = ImmutableSet.copyOf(Iterables.limit(questions4Test, countOfQuestions));
        Set<QuestionDataDto> questionForTestDtos = new HashSet<>();
        for (QuestionData question : questions4Test) {
            if (question.getQuestionType().equals(QuestionType.MATCH)) {
                shuffleMatchableQuestion((MatchableQuestion) question);
                questionForTestDtos.add(customQuestionMapper.toDto(question));
            } else if (question.getQuestionType().equals(QuestionType.SELECT) || question.getQuestionType().equals(QuestionType.SEQUENCE)) {
                shuffleSelectableQuestion((SelectableQuestion) question);
                questionForTestDtos.add(customQuestionMapper.toDto(question));
            } else { // if type is WRITE
                questionForTestDtos.add(customQuestionMapper.toDto(question));
            }
        }
        return questionForTestDtos;
    }

    public AfterCheckTestDto checkTest(List<CheckTestDto> questionListForCheck) {
        List<AfterCheckQuestionDto> afterCheckQuestionList = new ArrayList<>();
        PassedTest passedTest = new PassedTest();
        List<PassedQuestionData> passedQuestions = new ArrayList<>();
        double totalRightAnsDegree = 0;
        for (CheckTestDto questionForCheck : questionListForCheck) {
            Optional<QuestionData> originalQuestion = questionRepo.findById(questionForCheck.getQuestionId());
            if (originalQuestion.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            if (originalQuestion.get().getQuestionType().equals(QuestionType.WRITE)) {
                totalRightAnsDegree = treatWriteQuestion(afterCheckQuestionList, passedTest, passedQuestions, totalRightAnsDegree, questionForCheck, originalQuestion);
            } else if (originalQuestion.get().getQuestionType().equals(QuestionType.SELECT)) {
                totalRightAnsDegree = treatSelectQuestion(afterCheckQuestionList, passedTest, passedQuestions, totalRightAnsDegree, questionForCheck, originalQuestion);
            }

        }
        totalRightAnsDegree = Math.round(totalRightAnsDegree * 100 / questionListForCheck.size());
        passedTest.setPassedQuestions(passedQuestions);
        passedTest.setRatingInPercent((int) totalRightAnsDegree);
        passedTest.setPassedAt(OffsetDateTime.now());
        passedTestRepo.save(passedTest);
        return new AfterCheckTestDto(afterCheckQuestionList, (long) totalRightAnsDegree);
    }

    private double treatSelectQuestion(List<AfterCheckQuestionDto> afterCheckQuestionList, PassedTest passedTest, List<PassedQuestionData> passedQuestions, double totalRightAnsDegree, CheckTestDto questionForCheck, Optional<QuestionData> originalQuestion) {
        PassedSelectableQuestion passedSelectableQuestion = new PassedSelectableQuestion(originalQuestion.get(), passedTest);
        CheckQuestion checkQuestion = new CheckQuestion(originalQuestion.get(), questionForCheck.getAnswers(), passedSelectableQuestion);
        checkSelectQuestion(checkQuestion);
        totalRightAnsDegree += checkQuestion.getRating();

        passedSelectableQuestion.setUserAnswers((List<PassedSelectableAnswer>) checkQuestion.getUserAnswers());
        passedQuestions.add(passedSelectableQuestion);

        List<UserSelectableAnswer> userAnswerIds = new ArrayList<>();
        passedSelectableQuestion.getUserAnswers().forEach(it -> userAnswerIds.add(new UserSelectableAnswer(it.getUserAnswer().getId(), it.getRight())));

        afterCheckQuestionList.add(new AfterCheckQuestionDto(originalQuestion.get(), userAnswerIds));
        return totalRightAnsDegree;
    }

    private double treatWriteQuestion(List<AfterCheckQuestionDto> afterCheckQuestionList, PassedTest passedTest, List<PassedQuestionData> passedQuestions, double totalRightAnsDegree, CheckTestDto questionForCheck, Optional<QuestionData> originalQuestion) {
        PassedWriteableQuestion passedWriteableQuestion = new PassedWriteableQuestion(originalQuestion.get(), passedTest);
        CheckQuestion checkQuestion = new CheckQuestion(originalQuestion.get(), questionForCheck.getAnswers(), passedWriteableQuestion);
        checkWriteQuestion(checkQuestion);
        totalRightAnsDegree += checkQuestion.getRating();

        passedWriteableQuestion.setUserAnswers((List<PassedWriteableAnswer>) checkQuestion.getUserAnswers());
        passedQuestions.add(passedWriteableQuestion);

        List<UserWriteableAnswer> userAnswers = new ArrayList<>();
        passedWriteableQuestion.getUserAnswers().forEach(it -> userAnswers.add(new UserWriteableAnswer(it.getUserAnswer(), it.getRight())));

        afterCheckQuestionList.add(new AfterCheckQuestionDto(originalQuestion.get(), userAnswers));
        return totalRightAnsDegree;
    }

    private void checkSelectQuestion(CheckQuestion checkQuestion) {
        double rightAnswerDegree = 0;
        boolean userAnswerIsRight;
        List<PassedSelectableAnswer> userAnswerList = new ArrayList<>();
        List<AnswerOption> originalRightAnswers = ((SelectableQuestion) checkQuestion.getOriginalQuestion()).getAnswerOptionList()
                .stream().filter(AnswerOption::getRight).collect(Collectors.toList());
        List<Long> userAnswerIds = new ArrayList<>();
        ((List<Integer>) checkQuestion.getUserAnswers()).forEach(it -> userAnswerIds.add(it.longValue()));
        for (Long userAnsId : userAnswerIds) {
            userAnswerIsRight = false;
            for (AnswerOption origAns : originalRightAnswers) {
                if (origAns.getId().equals(userAnsId)) {
                    // if user gave right answers more than in the original question, then the answer is making true and adding points
                    if (userAnswerIds.size() <= originalRightAnswers.size()) {
                        rightAnswerDegree += 1.0 / originalRightAnswers.size();
                        userAnswerIsRight = true;
                    }
                    break;
                }
            }
            Optional<AnswerOption> userAnswerOption = ((SelectableQuestion) checkQuestion.getOriginalQuestion()).getAnswerOptionList()
                    .stream().filter(it -> it.getId().equals(userAnsId)).findFirst();
            PassedSelectableAnswer passedAnswer = new PassedSelectableAnswer(userAnswerOption.get(), userAnswerIsRight,
                    (PassedSelectableQuestion) checkQuestion.getPassedQuestion());
            userAnswerList.add(passedAnswer);
        }
        checkQuestion.setUserAnswers(userAnswerList);
        checkQuestion.setRating(rightAnswerDegree);
    }

    private void checkWriteQuestion(CheckQuestion checkQuestion) {
        double rightAnswerDegree = 0;
        boolean userAnswerIsRight;
        List<PassedWriteableAnswer> userAnswerList = new ArrayList<>();
        List<WriteableAnswerOption> originalAnswers = ((WriteableQuestion) checkQuestion.getOriginalQuestion()).getAnswerOptionWriteList();
        for (String userAns : (List<String>) checkQuestion.getUserAnswers()) {
            userAnswerIsRight = false;
            for (WriteableAnswerOption rightAnswer : originalAnswers) {
                if (rightAnswer.getStrict()) {
                    if (rightAnswer.getAnswer().equals(userAns)) {
                        userAnswerIsRight = true;
                        rightAnswerDegree += 1.0 / originalAnswers.size();
                        break;
                    }
                } else {
                    if (rightAnswer.getAnswer().toLowerCase().equals(userAns.toLowerCase())) {
                        userAnswerIsRight = true;
                        rightAnswerDegree += 1.0 / originalAnswers.size();
                        break;
                    }
                }
            }
            PassedWriteableAnswer passedAnswer = new PassedWriteableAnswer(userAns, userAnswerIsRight,
                    (PassedWriteableQuestion) checkQuestion.getPassedQuestion());
            userAnswerList.add(passedAnswer);
        }
        checkQuestion.setUserAnswers(userAnswerList);
        checkQuestion.setRating(rightAnswerDegree);
    }

    private void shuffleMatchableQuestion(MatchableQuestion matchableQuestion) {
        List<AnswerOption> keys = new ArrayList<>();
        List<AnswerOption> values = new ArrayList<>();
        for (MatchableAnswerOption answer : matchableQuestion.getMatchableAnswerOptionList()) {
            keys.add(answer.getKey());
            values.add(answer.getValue());
        }
        Collections.shuffle(keys);
        Collections.shuffle(values);
        List<MatchableAnswerOption> newMatchableAnswers = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            newMatchableAnswers.add(new MatchableAnswerOption(keys.get(i), values.get(i)));
        }
        matchableQuestion.setMatchableAnswerOptionList(newMatchableAnswers);
    }

    private void shuffleSelectableQuestion(SelectableQuestion selectableQuestion) {
        Collections.shuffle(selectableQuestion.getAnswerOptionList());
    }


/*    public checkTest() {

    }*/

}
