package com.example.javaserver.testing.new_v.service;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.testing.new_v.config.QuestionType;
import com.example.javaserver.testing.new_v.dto.answer.for_test.result.*;
import com.example.javaserver.testing.new_v.model.answer.MatchableAnswerOption;
import com.example.javaserver.testing.new_v.model.answer.WriteableAnswerOption;
import com.example.javaserver.testing.new_v.model.saving_result.PassedTestN;
import com.example.javaserver.testing.new_v.model.saving_result.answer.PassedMatchableAnswer;
import com.example.javaserver.testing.new_v.model.saving_result.answer.PassedSelectableAnswer;
import com.example.javaserver.testing.new_v.model.saving_result.answer.PassedWriteableAnswer;
import com.example.javaserver.testing.new_v.model.saving_result.question.PassedMatchableQuestion;
import com.example.javaserver.testing.new_v.model.saving_result.question.PassedQuestionData;
import com.example.javaserver.testing.new_v.model.saving_result.question.PassedSelectableQuestion;
import com.example.javaserver.testing.new_v.model.saving_result.question.PassedWriteableQuestion;
import com.example.javaserver.testing.new_v.repo.AnswerOptionRepo;
import com.example.javaserver.testing.new_v.repo.PassedTestRepoN;
import com.example.javaserver.testing.new_v.repo.question.QuestionDataRepo;
import com.example.javaserver.testing.new_v.service.model.CheckQuestion;
import com.example.javaserver.testing.new_v.service.model.MatchPair;
import com.example.javaserver.testing.new_v.service.model.ResultOfSomethingChecking;
import com.example.javaserver.testing.new_v.dto.answer.for_test.checking.CheckTestDto;
import com.example.javaserver.testing.new_v.dto.mapper.CustomQuestionMapper;
import com.example.javaserver.testing.new_v.dto.question.QuestionDataDto;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.testing.new_v.model.answer.AnswerOption;
import com.example.javaserver.testing.new_v.model.question.MatchableQuestion;
import com.example.javaserver.testing.new_v.model.question.QuestionData;
import com.example.javaserver.testing.new_v.model.question.SelectableQuestion;
import com.example.javaserver.testing.new_v.model.question.WriteableQuestion;
import com.example.javaserver.testing.theme.ThemeRepo;
import com.example.javaserver.user.model.User;
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
public class TestServiceN {
    private final AnswerOptionRepo answerOptionRepo;
    private final PassedTestRepoN passedTestRepoN;
    private final CustomQuestionMapper customQuestionMapper;
    private final UserRepo userRepo;
    private final ThemeRepo themeRepo;
    private final QuestionDataRepo questionDataRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    public TestServiceN(AnswerOptionRepo answerOptionRepo, PassedTestRepoN passedTestRepoN, CustomQuestionMapper customQuestionMapper, UserRepo userRepo, ThemeRepo themeRepo, QuestionDataRepo questionDataRepo) {
        this.answerOptionRepo = answerOptionRepo;
        this.passedTestRepoN = passedTestRepoN;
        this.customQuestionMapper = customQuestionMapper;
        this.userRepo = userRepo;
        this.themeRepo = themeRepo;
        this.questionDataRepo = questionDataRepo;
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
        List<QuestionData> questionsForTest = questionDataRepo.findAllByThemeId(themeId);
        Collections.shuffle(questionsForTest);
        countOfQuestions = Math.min(countOfQuestions, questionsForTest.size());
        questionsForTest = questionsForTest.subList(0, countOfQuestions);
        Set<QuestionDataDto> questionForTestDtos = new HashSet<>();
        for (QuestionData question : questionsForTest) {
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

    public AfterCheckTestDto checkTest(List<CheckTestDto> questionListForCheck, UserDetailsImp userDetails) {
        if (questionListForCheck.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Передан пустой список вопросов на проверку");
        }
        List<AfterCheckQuestionDto> afterCheckQuestionList = new ArrayList<>();
        PassedTestN passedTestN = new PassedTestN();
        List<PassedQuestionData> passedQuestions = new ArrayList<>();
        double totalRightAnsDegree = 0;
        Optional<QuestionData> originalQuestion = Optional.empty();
        for (CheckTestDto questionForCheck : questionListForCheck) {
            originalQuestion = questionDataRepo.findById(questionForCheck.getQuestionId());
            if (originalQuestion.isEmpty()) {
                String answer = String.format(doesntExistById, questionForCheck.getQuestionId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Вопроса" + answer);
            }
            if (originalQuestion.get().getQuestionType().equals(QuestionType.WRITE)) {
                totalRightAnsDegree = treatWriteQuestion(afterCheckQuestionList, passedTestN, passedQuestions, totalRightAnsDegree, questionForCheck, originalQuestion);
            } else if (originalQuestion.get().getQuestionType().equals(QuestionType.SELECT)) {
                totalRightAnsDegree = treatSelectQuestion(afterCheckQuestionList, passedTestN, passedQuestions, totalRightAnsDegree, questionForCheck, originalQuestion);
            } else if (originalQuestion.get().getQuestionType().equals(QuestionType.MATCH)) {
                totalRightAnsDegree = treatMatchQuestion(afterCheckQuestionList, passedTestN, passedQuestions, totalRightAnsDegree, questionForCheck, originalQuestion);
            }
        }
        totalRightAnsDegree = Math.round(totalRightAnsDegree * 100 / questionListForCheck.size());
        saveTest(originalQuestion.get(), userDetails, passedTestN, passedQuestions, (int) totalRightAnsDegree);
        return new AfterCheckTestDto(afterCheckQuestionList, (long) totalRightAnsDegree);
    }

    private void saveTest(QuestionData originalQuestion, UserDetailsImp userDetails, PassedTestN passedTestN, List<PassedQuestionData> passedQuestions, int totalRightAnsDegree) {
        passedTestN.setPassedQuestions(passedQuestions);
        passedTestN.setRatingInPercent(totalRightAnsDegree);
        passedTestN.setPassedAt(OffsetDateTime.now());
        passedTestN.setUser(fetchUser(userDetails));
        passedTestN.setTheme(originalQuestion.getTheme());
        passedTestRepoN.save(passedTestN);
    }

    private double treatSelectQuestion(List<AfterCheckQuestionDto> afterCheckQuestionList, PassedTestN passedTestN, List<PassedQuestionData> passedQuestions, double totalRightAnsDegree, CheckTestDto questionForCheck, Optional<QuestionData> originalQuestion) {
        PassedSelectableQuestion passedSelectableQuestion = new PassedSelectableQuestion(originalQuestion.get(), passedTestN);
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

    private double treatWriteQuestion(List<AfterCheckQuestionDto> afterCheckQuestionList, PassedTestN passedTestN, List<PassedQuestionData> passedQuestions, double totalRightAnsDegree, CheckTestDto questionForCheck, Optional<QuestionData> originalQuestion) {
        PassedWriteableQuestion passedWriteableQuestion = new PassedWriteableQuestion(originalQuestion.get(), passedTestN);
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

    private double treatMatchQuestion(List<AfterCheckQuestionDto> afterCheckQuestionList, PassedTestN passedTestN, List<PassedQuestionData> passedQuestions, double totalRightAnsDegree, CheckTestDto questionForCheck, Optional<QuestionData> originalQuestion) {
        PassedMatchableQuestion passedMatchableQuestion = new PassedMatchableQuestion(originalQuestion.get(), passedTestN);
        CheckQuestion checkQuestion = new CheckQuestion(originalQuestion.get(), questionForCheck.getAnswers(), passedMatchableQuestion);
        checkMatchQuestion(checkQuestion);
        totalRightAnsDegree += checkQuestion.getRating();

        passedMatchableQuestion.setUserAnswers((List<PassedMatchableAnswer>) checkQuestion.getUserAnswers());
        passedQuestions.add(passedMatchableQuestion);

        List<UserMatchableAnswer> userAnswers = new ArrayList<>();
        passedMatchableQuestion.getUserAnswers().forEach(it -> userAnswers.add(
                new UserMatchableAnswer(it.getKey().getId(), it.getValue().getId(), it.getRight()))
        );
        afterCheckQuestionList.add(new AfterCheckQuestionDto(originalQuestion.get(), userAnswers));
        return totalRightAnsDegree;

    }

    private void checkMatchQuestion(CheckQuestion checkQuestion) {
        double rightAnswerDegree = 0;
        boolean userAnswerIsRight;
        List<PassedMatchableAnswer> userAnswerList = new ArrayList<>();
        List<MatchableAnswerOption> originalAnswers = ((MatchableQuestion) checkQuestion.getOriginalQuestion()).getMatchableAnswerOptionList();
        List<MatchPair> userAnswerPairs = new ArrayList<>();
        List<MatchPair> originalAnswerPairs = new ArrayList<>();
        for (LinkedHashMap<String, Integer> userPair : ((List<LinkedHashMap<String, Integer>>) checkQuestion.getUserAnswers())) {
            userAnswerList.add(new PassedMatchableAnswer(
                    answerOptionRepo.findById(userPair.get("key").longValue()).get(),
                    answerOptionRepo.findById(userPair.get("value").longValue()).get(),
                    false, (PassedMatchableQuestion) checkQuestion.getPassedQuestion()
            ));
        }
        for (MatchableAnswerOption originalPair : originalAnswers) {
            originalAnswerPairs.add(new MatchPair(
                    originalPair.getKey().getId().intValue(), originalPair.getValue().getId().intValue()
            ));
        }

        for (PassedMatchableAnswer userMatch : userAnswerList) {
            for (MatchPair originalPair : originalAnswerPairs) {
                if (userMatch.getKey().getId() == originalPair.getKeyId().longValue()) {
                    if (userMatch.getValue().getId() == originalPair.getValueId().longValue()) {
                        userMatch.setRight(true);
                        rightAnswerDegree += 1.0 / originalAnswerPairs.size();
                        break;
                    }
                }
            }
        }
        checkQuestion.setUserAnswers(userAnswerList);
        checkQuestion.setRating(rightAnswerDegree);
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
        String userAns = ((List<String>) checkQuestion.getUserAnswers()).get(0);
        userAnswerIsRight = false;
        for (WriteableAnswerOption rightAnswer : originalAnswers) {
            if (rightAnswer.getStrict()) {
                if (rightAnswer.getAnswer().equals(userAns)) {
                    userAnswerIsRight = true;
                    rightAnswerDegree++;
                    break;
                }
            } else {
                if (rightAnswer.getAnswer().toLowerCase().equals(userAns.toLowerCase())) {
                    userAnswerIsRight = true;
                    rightAnswerDegree++;
                    break;
                }
            }
        }
        PassedWriteableAnswer passedAnswer = new PassedWriteableAnswer(userAns, userAnswerIsRight,
                (PassedWriteableQuestion) checkQuestion.getPassedQuestion());
        userAnswerList.add(passedAnswer);
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


    private User fetchUser(UserDetailsImp userDetails) {
        Optional<User> user = userRepo.findById(userDetails.getId());
        return user.orElse(null);
    }

}
