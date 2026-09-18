package com.example.germanlearning.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.germanlearning.entity.Module;
import com.example.germanlearning.entity.Question;
import com.example.germanlearning.entity.QuestionType;
import com.example.germanlearning.entity.Quiz;
import com.example.germanlearning.entity.Topic;
import com.example.germanlearning.repository.QuestionRepository;
import com.example.germanlearning.repository.QuizRepository;
import com.example.germanlearning.repository.TopicRepository;

@Component
public class QuestionBankInitializer {

    @Transactional
    public void seedAllTopicQuestions(
            Module mod1, Module mod2, Module mod3, Module mod4,
            TopicRepository topicRepository,
            QuizRepository quizRepository,
            QuestionRepository questionRepository) {

        if (mod1 != null && mod1.getTopics() != null) {
            seedModule1Questions(mod1, quizRepository, questionRepository);
        }
        if (mod2 != null && mod2.getTopics() != null) {
            seedModule2Questions(mod2, quizRepository, questionRepository);
        }
        if (mod3 != null && mod3.getTopics() != null) {
            seedModule3Questions(mod3, quizRepository, questionRepository);
        }
        if (mod4 != null && mod4.getTopics() != null) {
            seedModule4Questions(mod4, quizRepository, questionRepository);
        }
    }

    private void saveQuestionsForTopic(
            Module module, Topic topic, List<Question> candidates,
            QuizRepository quizRepository, QuestionRepository questionRepository) {

        if (topic == null || candidates == null || candidates.isEmpty()) {
            return;
        }

        // Get or create Quiz for Topic
        List<Quiz> quizzes = quizRepository.findByTopicId(topic.getId());
        Quiz quiz;
        if (quizzes.isEmpty()) {
            quiz = new Quiz("Quiz: " + topic.getTitle(), "Topic Quiz testing comprehension of " + topic.getTitle(), 70, topic);
            quiz.setTimeLimitMinutes(15);
            quiz = quizRepository.save(quiz);
        } else {
            quiz = quizzes.get(0);
        }

        // Fetch existing question texts for idempotency
        List<Question> existing = questionRepository.findByTopicId(topic.getId());
        Set<String> existingTexts = existing.stream()
                .map(q -> q.getQuestionText().toLowerCase().replaceAll("\\s+", " ").trim())
                .collect(Collectors.toSet());

        List<Question> toSave = new ArrayList<>();
        for (Question q : candidates) {
            String norm = q.getQuestionText().toLowerCase().replaceAll("\\s+", " ").trim();
            if (!existingTexts.contains(norm)) {
                q.setModule(module);
                q.setTopic(topic);
                q.setQuiz(quiz);
                toSave.add(q);
                existingTexts.add(norm);
            }
        }

        if (!toSave.isEmpty()) {
            questionRepository.saveAll(toSave);
        }
    }

    // =========================================================================
    // MODULE 1 QUESTION BANKS (7 Topics)
    // =========================================================================
    private void seedModule1Questions(Module mod1, QuizRepository quizRepository, QuestionRepository questionRepository) {
        List<Topic> topics = mod1.getTopics();
        if (topics.isEmpty()) return;

        // Topic 1: Begrüßungen & Vorstellung
        if (topics.size() > 0) {
            Topic t = topics.get(0);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Wie begrüßt man jemanden formell am Abend?", QuestionType.MULTIPLE_CHOICE.name(), "Guten Abend", "Tschüss", "Gute Nacht", "Hallo", "A", "‘Guten Abend’ is the polite, formal greeting used from around 6 PM onwards. ‘Gute Nacht’ is only used when going to bed.", 1));
            qList.add(new Question("True or False: In German, ALL nouns must always be capitalized regardless of their position in a sentence.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "In German grammar, every single noun (der Name, das Buch, die Frau) begins with a capital letter.", 1));
            qList.add(new Question("Fill in the blank: Ich ____ aus Deutschland (kommen).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "komme", "Conjugation of kommen for ich is 'komme' (ich komme aus Deutschland).", 1));
            qList.add(new Question("Translate to German: ‘Nice to meet you (formal).’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Freut mich, Sie kennenzulernen / Freut mich Sie kennenzulernen", "‘Freut mich, Sie kennenzulernen’ is the polite German formula when meeting someone.", 1));
            qList.add(new Question("Translate to English: ‘Auf Wiedersehen!’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "Goodbye / Bye", "‘Auf Wiedersehen’ translates literally to 'until we see each other again' and means Goodbye.", 1));
            qList.add(new Question("Complete the question: ‘Wie ____ Sie?’ (heißen)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "heißen", "The formal question for someone's name is ‘Wie heißen Sie?’", 1));
            qList.add(new Question("What is the definite article for ‘Name’ in German? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "‘der Name’ is masculine in German.", 1));
            qList.add(new Question("Conjugate ‘wohnen’ for ‘du’: Wo ____ du?", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "wohnst", "The regular 2nd person singular ending is -st: ‘du wohnst’.", 1));
            qList.add(new Question("Which goodbye is typically used on the telephone in a formal setting?", QuestionType.MULTIPLE_CHOICE.name(), "Auf Wiederhören", "Tschüss", "Bis bald", "Hallo", "A", "On the telephone, 'Auf Wiederhören' (until we hear each other again) is the formal goodbye.", 1));
            qList.add(new Question("True or False: 'Hallo' is the formal German greeting for business meetings with clients.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "‘Hallo’ is informal; ‘Guten Tag’ or ‘Guten Morgen/Abend’ should be used in business meetings.", 1));
            qList.add(new Question("Fill in the preposition: Ich wohne ____ Berlin.", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "in", "Locations and cities use the preposition 'in': 'Ich wohne in Berlin'.", 1));
            qList.add(new Question("Fill in the preposition: Woher kommst du? - Ich komme ____ Spanien.", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "aus", "Origin uses 'aus': 'Ich komme aus [Land/Stadt]'.", 1));
            qList.add(new Question("Translate to German: ‘My name is Thomas.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Mein Name ist Thomas / Ich heiße Thomas", "Both 'Mein Name ist Thomas' and 'Ich heiße Thomas' are correct.", 1));
            qList.add(new Question("Conjugate ‘sprechen’ for ‘er’: Er ____ Deutsch.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "spricht", "‘sprechen’ has a vowel change in 3rd person singular: e -> i: ‘er spricht’.", 1));
            qList.add(new Question("Complete the greeting: ‘Guten ____!’ (morning)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Morgen", "Good morning in German is 'Guten Morgen'.", 1));
            saveQuestionsForTopic(mod1, t, qList, quizRepository, questionRepository);
        }

        // Topic 2: Die Verben "haben" und "sein"
        if (topics.size() > 1) {
            Topic t = topics.get(1);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Conjugate ‘sein’ for ‘ihr’: Ihr ____ sehr nett.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "seid", "Conjugation of sein for ihr is ‘ihr seid’.", 1));
            qList.add(new Question("Conjugate ‘haben’ for ‘du’: Du ____ ein Auto.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "hast", "The verb haben drops -b- in 2nd person: ‘du hast’.", 1));
            qList.add(new Question("True or False: In German, ‘ihr seid’ and the preposition ‘seit’ (since) have identical spelling.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "‘ihr seid’ ends in ‘d’, whereas preposition ‘seit’ (since) ends in ‘t’.", 1));
            qList.add(new Question("How do you express ‘I am hungry’ in German?", QuestionType.MULTIPLE_CHOICE.name(), "Ich habe Hunger", "Ich bin Hunger", "Ich mache Hunger", "Ich habe durstig", "A", "German uses the verb haben with Hunger: ‘Ich habe Hunger’ (literally 'I have hunger').", 1));
            qList.add(new Question("Conjugate ‘haben’ for ‘er’: Er ____ Zeit.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "hat", "Conjugation of haben for er/sie/es is ‘hat’.", 1));
            qList.add(new Question("Conjugate ‘sein’ for ‘wir’: Wir ____ zu Hause.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "sind", "Conjugation of sein for wir is ‘wir sind’.", 1));
            qList.add(new Question("Fill in the blank: Ich ____ müde (sein).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "bin", "Conjugation: ich bin müde.", 1));
            qList.add(new Question("Translate to German: ‘You have a lot of work (formal).’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Sie haben viel Arbeit", "Formal 'You have a lot of work' is 'Sie haben viel Arbeit'.", 1));
            qList.add(new Question("Translate to English: ‘Wir sind glücklich.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "We are happy", "‘Wir sind glücklich’ means 'We are happy'.", 1));
            qList.add(new Question("Which form of 'sein' corresponds to 'sie' (they)?", QuestionType.MULTIPLE_CHOICE.name(), "sind", "seid", "ist", "bist", "A", "'sie sind' is the 3rd person plural form of sein.", 1));
            qList.add(new Question("Complete the sentence: ‘Habt ____ Fragen?’ (you all)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "ihr", "The pronoun matching 'Habt' is 'ihr' (Habt ihr Fragen?).", 1));
            qList.add(new Question("Fill in the blank: Er ____ mein bester Freund (sein).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "ist", "Conjugation: er ist.", 1));
            qList.add(new Question("True or False: Predicate adjectives following 'sein' take adjective endings in German.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "Predicate adjectives following sein (e.g. Ich bin glücklich) do NOT take endings.", 1));
            qList.add(new Question("What is the fixed expression for 'at home' in German?", QuestionType.MULTIPLE_CHOICE.name(), "zu Hause", "nach Hause", "in Hause", "an Hause", "A", "'zu Hause' means at home (location); 'nach Hause' means towards home (direction).", 1));
            qList.add(new Question("Conjugate ‘haben’ for ‘ihr’: ____ ihr Durst?", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "Habt", "Conjugation of haben for ihr is 'Habt ihr'.", 1));
            saveQuestionsForTopic(mod1, t, qList, quizRepository, questionRepository);
        }

        // Topic 3: Personalpronomen & W-Fragen
        if (topics.size() > 2) {
            Topic t = topics.get(2);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Which W-word is used to ask for a static location (Where)?", QuestionType.MULTIPLE_CHOICE.name(), "Wo", "Wer", "Woher", "Wohin", "A", "‘Wo’ asks for static location (Where). ‘Wer’ asks for a person (Who).", 1));
            qList.add(new Question("True or False: German ‘Wer’ translates to English ‘Where’.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "False! German ‘Wer’ means ‘Who’, while German ‘Wo’ means ‘Where’.", 1));
            qList.add(new Question("Where does the conjugated verb go in a German W-question?", QuestionType.MULTIPLE_CHOICE.name(), "Position 2", "Position 1", "At the very end", "Position 3", "A", "In a W-question, the W-word is Position 1 and the conjugated verb is anchored to Position 2.", 1));
            qList.add(new Question("Fill in the question word: ‘____ machst du am Wochenende?’ (What)", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Was", "‘Was’ means What: ‘Was machst du am Wochenende?’", 1));
            qList.add(new Question("Translate to German: ‘Why do you learn German?’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Warum lernst du Deutsch? / Warum lernen Sie Deutsch?", "‘Warum’ means Why (Warum lernst du Deutsch?).", 1));
            qList.add(new Question("Translate to English: ‘Wann hast du Zeit?’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "When do you have time?", "‘Wann’ means When (When do you have time?).", 1));
            qList.add(new Question("Complete the question: ‘____ ist das?’ (Asking who the person is)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Wer", "‘Wer’ asks for a person (Who is that? -> Wer ist das?).", 1));
            qList.add(new Question("Fill in the question word: ‘____ alt bist du?’ (How)", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Wie", "‘Wie’ is used for age and manner: ‘Wie alt bist du?’", 1));
            qList.add(new Question("Which pronoun translates to English 'they' (plural)?", QuestionType.MULTIPLE_CHOICE.name(), "sie", "ihr", "wir", "er", "A", "'sie' (lowercase) is they.", 1));
            qList.add(new Question("True or False: 'Sie' with a capital S is the polite/formal 'you' (singular and plural).", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "Capitalized 'Sie' is always the formal address for 'you'.", 1));
            qList.add(new Question("Fill in the W-word for origin: ‘____ kommst du?’ (Where from)", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Woher", "‘Woher’ asks for place of origin: ‘Woher kommst du?’", 1));
            qList.add(new Question("Translate to English: ‘Wie heißen Sie?’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "What is your name? / What are you called?", "‘Wie heißen Sie?’ literally translates to 'How are you called?' meaning 'What is your name?'.", 1));
            qList.add(new Question("In the sentence 'Wo wohnst du?', what position is the pronoun 'du' in?", QuestionType.MULTIPLE_CHOICE.name(), "Position 3", "Position 1", "Position 2", "Position 4", "A", "Position 1 is 'Wo', Position 2 is 'wohnst', Position 3 is 'du'.", 1));
            qList.add(new Question("Translate to German: ‘Where do you live?’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Wo wohnst du? / Wo wohnen Sie?", "‘Wo wohnst du?’ (informal) or ‘Wo wohnen Sie?’ (formal).", 1));
            qList.add(new Question("Complete the W-question: ‘____ fängt der Film an?’ (When)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Wann", "‘Wann’ asks for time (When).", 1));
            saveQuestionsForTopic(mod1, t, qList, quizRepository, questionRepository);
        }

        // Topic 4: Regelmäßige Verben & Einfacher Satzbau
        if (topics.size() > 3) {
            Topic t = topics.get(3);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("What is the regular verb ending for ‘du’ in the present tense?", QuestionType.MULTIPLE_CHOICE.name(), "-st", "-e", "-t", "-en", "A", "The regular ending for du is -st (e.g., du lernst, du machst).", 1));
            qList.add(new Question("In a standard German declarative statement, which position must the conjugated verb occupy?", QuestionType.MULTIPLE_CHOICE.name(), "Position 2", "Position 1", "Position 3", "At the very end", "A", "The Golden Rule of German sentence structure states that the conjugated verb is anchored to Position 2.", 1));
            qList.add(new Question("Conjugate ‘lernen’ for ‘ich’: Ich ____ Deutsch.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "lerne", "Conjugation of regular verbs for ich ends in -e: ‘ich lerne’.", 1));
            qList.add(new Question("Conjugate ‘machen’ for ‘er’: Er ____ Hausaufgaben.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "macht", "3rd person singular regular ending is -t: ‘er macht’.", 1));
            qList.add(new Question("Conjugate ‘trinken’ for ‘wir’: Wir ____ Wasser.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "trinken", "wir form always takes -en: ‘wir trinken’.", 1));
            qList.add(new Question("Translate to German: ‘I learn German.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich lerne Deutsch", "‘Ich lerne Deutsch’ follows Subject (1) + Verb (2) + Object (3).", 1));
            qList.add(new Question("Translate to English: ‘Sie machen Kaffee.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "They make coffee / They are making coffee / You make coffee", "‘Sie machen Kaffee’ means 'They make coffee' or formal 'You make coffee'.", 1));
            qList.add(new Question("True or False: The regular verb ending for 'ihr' (you all) is '-t'.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! ‘ihr lernt’, ‘ihr macht’, ‘ihr wohnt’ take the ending -t.", 1));
            qList.add(new Question("Conjugate ‘spielen’ for ‘du’: Du ____ Fußball.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "spielst", "du form takes -st: ‘du spielst’.", 1));
            qList.add(new Question("Complete the sentence: ‘Er trinkt ____.’ (water)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Wasser", "‘Er trinkt Wasser’ (He drinks water).", 1));
            qList.add(new Question("What is the verb stem of ‘kochen’?", QuestionType.MULTIPLE_CHOICE.name(), "koch-", "koche-", "kochen-", "ko-", "A", "Drop the infinitive suffix -en to obtain the verb stem: 'koch-'.", 1));
            qList.add(new Question("Fill in the blank: Wir ____ in Berlin (wohnen).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "wohnen", "wir takes -en: ‘wir wohnen’.", 1));
            qList.add(new Question("Translate to German: ‘He plays football.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Er spielt Fußball", "‘Er spielt Fußball’ (spielen -> er spielt).", 1));
            qList.add(new Question("True or False: In German, 'wir' and 'sie/Sie' verb forms are almost always identical to the infinitive.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True, both wir and sie/Sie take -en ending.", 1));
            qList.add(new Question("Conjugate ‘hören’ for ‘ich’: Ich ____ Musik.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "höre", "ich takes -e: ‘ich höre’.", 1));
            saveQuestionsForTopic(mod1, t, qList, quizRepository, questionRepository);
        }

        // Topic 5: Artikel & Plural
        if (topics.size() > 4) {
            Topic t = topics.get(4);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("What is the definite article for a singular neuter noun in the nominative case?", QuestionType.MULTIPLE_CHOICE.name(), "das", "der", "die", "den", "A", "Neuter nominative definite article is ‘das’ (e.g., das Kind, das Buch).", 1));
            qList.add(new Question("What is the plural form of ‘das Buch’ (the book)?", QuestionType.MULTIPLE_CHOICE.name(), "die Bücher", "die Buche", "die Buchs", "die Büchern", "A", "‘das Buch’ forms its plural with Umlaut + -er: ‘die Bücher’.", 1));
            qList.add(new Question("What is the definite article for ALL plural nouns in the nominative case?", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "die", "All German nouns in the plural take the definite article ‘die’.", 1));
            qList.add(new Question("What is the indefinite article for a singular feminine noun? (ein / eine / einen)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "eine", "Feminine indefinite article is ‘eine’ (e.g., eine Frau, eine Tasche).", 1));
            qList.add(new Question("What is the plural of ‘der Tisch’ (the table)?", QuestionType.MULTIPLE_CHOICE.name(), "die Tische", "die Tischer", "die Tischs", "die Tischen", "A", "‘der Tisch’ plural is ‘die Tische’ (-e ending).", 1));
            qList.add(new Question("What is the plural of ‘das Auto’ (the car)?", QuestionType.MULTIPLE_CHOICE.name(), "die Autos", "die Autoe", "die Auton", "die Autö", "A", "Loanwords and words ending in vowels often take -s in plural: ‘die Autos’.", 1));
            qList.add(new Question("True or False: German plural nouns have an indefinite article (like 'einen' for plural).", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "Plural nouns have NO indefinite article in German (just 'Kinder', not 'ein Kinder').", 1));
            qList.add(new Question("What is the gender of ‘die Frau’?", QuestionType.MULTIPLE_CHOICE.name(), "Feminine", "Masculine", "Neuter", "Plural", "A", "‘die Frau’ is feminine (die).", 1));
            qList.add(new Question("Fill in the article: Hier ist ____ Mann (indefinite masculine).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "ein", "Masculine nominative indefinite article is ‘ein’.", 1));
            qList.add(new Question("Fill in the article: Das ist ____ Tasche (indefinite feminine).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "eine", "Feminine nominative indefinite article is ‘eine’.", 1));
            qList.add(new Question("What is the plural of ‘der Lehrer’ (the male teacher)?", QuestionType.MULTIPLE_CHOICE.name(), "die Lehrer", "die Lehrere", "die Lehrers", "die Lehrern", "A", "Masculine nouns ending in -er generally don't change in plural: ‘die Lehrer’.", 1));
            qList.add(new Question("Translate to German: ‘the children’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "die Kinder", "‘die Kinder’ is the plural of das Kind.", 1));
            qList.add(new Question("Translate to English: ‘die Taschen’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "the bags / the pockets", "‘die Taschen’ means the bags.", 1));
            qList.add(new Question("What is the definite article for ‘Kind’ (child)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "das", "‘das Kind’ is neuter.", 1));
            qList.add(new Question("Complete the pair: Singular: das Haus -> Plural: die ____.", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Häuser", "Plural of das Haus is ‘die Häuser’.", 1));
            saveQuestionsForTopic(mod1, t, qList, quizRepository, questionRepository);
        }

        // Topic 6: Wochentage, Monate & Jahreszeiten
        if (topics.size() > 5) {
            Topic t = topics.get(5);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Which preposition is used with days of the week (e.g. Monday)?", QuestionType.MULTIPLE_CHOICE.name(), "am", "im", "um", "an", "A", "Days of the week use ‘am’ (am Montag, am Dienstag).", 1));
            qList.add(new Question("Which preposition is used with months and seasons (e.g. summer, July)?", QuestionType.MULTIPLE_CHOICE.name(), "im", "am", "um", "in", "A", "Months and seasons use ‘im’ (im Sommer, im Juli).", 1));
            qList.add(new Question("Fill in the preposition: ____ Montag arbeite ich.", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Am", "Days take ‘Am’: ‘Am Montag arbeite ich’.", 1));
            qList.add(new Question("Fill in the preposition: Mein Geburtstag ist ____ Oktober.", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "im", "Months take ‘im’: ‘im Oktober’.", 1));
            qList.add(new Question("Translate to English: ‘der Frühling’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "Spring / the spring", "‘der Frühling’ means spring.", 1));
            qList.add(new Question("Translate to German: ‘Wednesday’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Mittwoch / der Mittwoch", "Wednesday is ‘Mittwoch’ in German.", 1));
            qList.add(new Question("What day comes after Freitag (Friday)?", QuestionType.MULTIPLE_CHOICE.name(), "Samstag", "Donnerstag", "Sonntag", "Montag", "A", "Saturday in German is ‘Samstag’.", 1));
            qList.add(new Question("True or False: All 12 calendar months are grammatically masculine (der) in German.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! All months (der Januar, der Februar...) are masculine.", 1));
            qList.add(new Question("Fill in the preposition: Was machst du ____ Wochenende?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "am", "Weekend takes ‘am’: ‘am Wochenende’.", 1));
            qList.add(new Question("Which season includes Dezember, Januar, and Februar?", QuestionType.MULTIPLE_CHOICE.name(), "der Winter", "der Sommer", "der Herbst", "der Frühling", "A", "Winter includes December, January, and February.", 1));
            qList.add(new Question("Translate to German: ‘Sunday’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Sonntag / der Sonntag", "Sunday in German is ‘Sonntag’.", 1));
            qList.add(new Question("Complete the sequence: Montag, Dienstag, ____, Donnerstag.", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Mittwoch", "The sequence is Montag, Dienstag, Mittwoch, Donnerstag.", 1));
            qList.add(new Question("What is the definite article for all days of the week? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "All days of the week are masculine: ‘der Montag’, ‘der Dienstag’...", 1));
            qList.add(new Question("Translate to English: ‘Im Sommer reisen wir nach Spanien.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "In summer we travel to Spain / In the summer we travel to Spain", "‘Im Sommer reisen wir nach Spanien’ means 'In summer we travel to Spain'.", 1));
            qList.add(new Question("Translate to German: ‘in autumn / in fall’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "im Herbst", "‘im Herbst’ means in autumn.", 1));
            saveQuestionsForTopic(mod1, t, qList, quizRepository, questionRepository);
        }

        // Topic 7: Hobbys & Alltag
        if (topics.size() > 6) {
            Topic t = topics.get(6);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Where does the separable prefix ‘auf’ go in: ‘Ich stehe um 7 Uhr ____ (aufstehen)’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "auf", "In a present tense main clause, the separable prefix goes to the very end.", 1));
            qList.add(new Question("Which preposition is used for specific clock times (e.g. 7 o'clock)?", QuestionType.MULTIPLE_CHOICE.name(), "um", "am", "im", "an", "A", "Clock times always use ‘um’ (e.g., um 7 Uhr).", 1));
            qList.add(new Question("Translate to English: ‘Was sind deine Hobbys?’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "What are your hobbies?", "‘Was sind deine Hobbys?’ means 'What are your hobbies?'.", 1));
            qList.add(new Question("Translate to German: ‘I like to cook in the evening.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich koche gern am Abend / Ich koche gerne am Abend", "‘Ich koche gern am Abend’ expresses liking to cook.", 1));
            qList.add(new Question("True or False: When verbs like ‘lesen’ and ‘kochen’ are used as hobby nouns, they are capitalized in German.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! Nominalized verbs become neuter nouns and are capitalized: das Lesen, das Kochen.", 1));
            qList.add(new Question("Conjugate ‘lesen’ for ‘du’: Du ____ gern Bücher.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "liest", "‘lesen’ has a vowel change: e -> ie: ‘du liest’.", 1));
            qList.add(new Question("Conjugate ‘fernsehen’ for ‘er’: Er ____ jeden Abend fern.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "sieht", "‘sehen’ has vowel change: ‘er sieht ... fern’.", 1));
            qList.add(new Question("Fill in the preposition: Wann stehst du ____ 7 Uhr auf?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "um", "Clock times use ‘um’ (um 7 Uhr).", 1));
            qList.add(new Question("What is the standard German expression for 'to exercise / do sports'?", QuestionType.MULTIPLE_CHOICE.name(), "Sport machen", "Sport tun", "Sport spielen", "Sport haben", "A", "‘Sport machen’ or ‘Sport treiben’ is the standard German phrase.", 1));
            qList.add(new Question("Complete the sentence: ‘In meiner Freizeit treffe ich ____.’ (friends)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Freunde", "‘Freunde treffen’ means to meet friends.", 1));
            qList.add(new Question("Conjugate ‘treffen’ for ‘du’: Du ____ deine Freunde.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "triffst", "‘treffen’ has a vowel change: e -> i: ‘du triffst’.", 1));
            qList.add(new Question("Translate to German: ‘I get up at 7 o'clock.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich stehe um 7 Uhr auf / Ich stehe um sieben Uhr auf", "‘Ich stehe um 7 Uhr auf’ with separable prefix at the end.", 1));
            qList.add(new Question("Translate to English: ‘arbeiten gehen’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "to go to work / go to work", "‘arbeiten gehen’ means to go to work.", 1));
            qList.add(new Question("What is the definite article for ‘Freizeit’ (free time)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "die", "Nouns ending in -heit/-keit/-zeit are feminine: ‘die Freizeit’.", 1));
            qList.add(new Question("True or False: In a question with a separable verb, the prefix stays attached to the verb stem at Position 2.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "In both statements and W-questions, the separable prefix moves to the very end: ‘Wann stehst du auf?’", 1));
            saveQuestionsForTopic(mod1, t, qList, quizRepository, questionRepository);
        }
    }

    // =========================================================================
    // MODULE 2 QUESTION BANKS (7 Topics)
    // =========================================================================
    private void seedModule2Questions(Module mod2, QuizRepository quizRepository, QuestionRepository questionRepository) {
        List<Topic> topics = mod2.getTopics();
        if (topics.isEmpty()) return;

        // Topic 1: Articles & Plural Forms
        if (topics.size() > 0) {
            Topic t = topics.get(0);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("What is the plural form of ‘das Haus’ (the house)?", QuestionType.MULTIPLE_CHOICE.name(), "die Häuser", "die Hause", "die Häusen", "die Hausen", "A", "‘das Haus’ pluralizes with Umlaut + -er: ‘die Häuser’.", 1));
            qList.add(new Question("What is the plural of ‘die Straße’ (the street)?", QuestionType.MULTIPLE_CHOICE.name(), "die Straßen", "die Straße", "die Straßer", "die Straßes", "A", "Feminine nouns ending in -e take -n in the plural: ‘die Straßen’.", 1));
            qList.add(new Question("What is the plural of ‘der Zug’ (the train)?", QuestionType.MULTIPLE_CHOICE.name(), "die Züge", "die Zügen", "die Zugs", "die Züger", "A", "‘der Zug’ plural is ‘die Züge’ (Umlaut + -e).", 1));
            qList.add(new Question("What is the plural of ‘das Fahrrad’ (the bicycle)?", QuestionType.MULTIPLE_CHOICE.name(), "die Fahrräder", "die Fahrrade", "die Fahrrads", "die Fahrrädern", "A", "‘das Fahrrad’ -> ‘die Fahrräder’ (Umlaut + -er).", 1));
            qList.add(new Question("True or False: ‘das Gebäude’ (the building) changes its spelling in the plural to ‘die Gebäuden’.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "‘das Gebäude’ has no suffix change in the plural: ‘die Gebäude’.", 1));
            qList.add(new Question("What is the definite article for ‘Bus’ (bus)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "‘der Bus’ is masculine.", 1));
            qList.add(new Question("What is the plural of ‘der Bus’?", QuestionType.MULTIPLE_CHOICE.name(), "die Busse", "die Buse", "die Bussen", "die Bus", "A", "‘der Bus’ doubles the 's' in the plural: ‘die Busse’.", 1));
            qList.add(new Question("What is the plural of ‘die Frau’ (the woman)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Frauen", "‘die Frau’ -> ‘die Frauen’.", 1));
            qList.add(new Question("Translate to German: ‘the men’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "die Männer", "‘der Mann’ -> ‘die Männer’.", 1));
            qList.add(new Question("Translate to English: ‘die Gebäude’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "the buildings", "‘die Gebäude’ means the buildings.", 1));
            qList.add(new Question("What is the definite article for ‘Stadt’ (city)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "die", "‘die Stadt’ is feminine.", 1));
            qList.add(new Question("What is the plural of ‘die Stadt’?", QuestionType.MULTIPLE_CHOICE.name(), "die Städte", "die Stadten", "die Städt", "die Städter", "A", "‘die Stadt’ -> ‘die Städte’ (Umlaut + -e).", 1));
            qList.add(new Question("Complete the sentence: ‘Hier stehen viele ____.’ (cars)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Autos", "Plural of das Auto is ‘Autos’.", 1));
            qList.add(new Question("True or False: All feminine nouns ending in ‘-ung’, ‘-heit’, ‘-keit’ form their plural with ‘-en’.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! Suffixes -ung, -heit, -keit reliably take -en in plural.", 1));
            qList.add(new Question("What is the plural of ‘das Kind’ (the child)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Kinder", "‘das Kind’ -> ‘die Kinder’.", 1));
            saveQuestionsForTopic(mod2, t, qList, quizRepository, questionRepository);
        }

        // Topic 2: Negation: "kein" vs. "nicht"
        if (topics.size() > 1) {
            Topic t = topics.get(1);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("When do you use ‘kein’ instead of ‘nicht’ in German?", QuestionType.MULTIPLE_CHOICE.name(), "To negate nouns preceded by ein or having no article", "To negate conjugated verbs", "To negate adjectives and adverbs", "To negate proper names", "A", "‘kein’ negates nouns with an indefinite article or nouns with no article.", 1));
            qList.add(new Question("Which negation word completes: ‘Ich habe ____ Zeit’ (time)?", QuestionType.MULTIPLE_CHOICE.name(), "keine", "nicht", "kein", "keinen", "A", "Zeit is feminine (die Zeit, no article), so it takes ‘keine’.", 1));
            qList.add(new Question("Which negation word completes: ‘Das ist ____ mein Buch’?", QuestionType.MULTIPLE_CHOICE.name(), "nicht", "kein", "keine", "keinen", "A", "Possessive pronouns (mein Buch) are negated with ‘nicht’.", 1));
            qList.add(new Question("Fill in the negation word: Ich verstehe das ____ (I don't understand that).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "nicht", "Verbs and actions are negated with ‘nicht’.", 1));
            qList.add(new Question("Fill in the negation word: Ich habe ____ Hund (der Hund, accusative).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "keinen", "Masculine accusative noun takes ‘keinen’.", 1));
            qList.add(new Question("Translate to German: ‘I don't have a car.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich habe kein Auto", "‘Auto’ is neuter, so use ‘kein Auto’.", 1));
            qList.add(new Question("Translate to English: ‘Er kommt heute nicht.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "He is not coming today / He does not come today", "‘nicht’ negates the verb action.", 1));
            qList.add(new Question("True or False: Adjectives are negated with ‘kein’ (e.g. *kein gut).", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "Adjectives are negated with ‘nicht’ (nicht gut, nicht teuer).", 1));
            qList.add(new Question("Fill in the blank: Das ist ____ Problem (das Problem).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "kein", "Neuter nominative takes ‘kein’: ‘kein Problem’.", 1));
            qList.add(new Question("Complete the sentence: ‘Ich trinke ____ Kaffee’ (no coffee).", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "keinen", "Masculine accusative: ‘keinen Kaffee’.", 1));
            qList.add(new Question("Which negation word is used before proper names (e.g., That is not Thomas)?", QuestionType.MULTIPLE_CHOICE.name(), "nicht", "kein", "keine", "keinen", "A", "Proper names take ‘nicht’ (Das ist nicht Thomas).", 1));
            qList.add(new Question("Translate to German: ‘She is not tired.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Sie ist nicht müde", "Predicate adjective 'müde' is negated with 'nicht'.", 1));
            qList.add(new Question("Fill in the blank: Wir haben ____ Kinder (plural).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "keine", "Plural nouns take ‘keine’: ‘keine Kinder’.", 1));
            qList.add(new Question("True or False: ‘nicht’ stands at the very end of a simple sentence when negating the whole verb action.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! ‘Ich schlafe nicht’, ‘Er kommt nicht’.", 1));
            qList.add(new Question("Complete the sentence: ‘Das ist ____ Katze, sondern ein Hund.’ (die Katze)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "keine", "Feminine takes ‘keine Katze’.", 1));
            saveQuestionsForTopic(mod2, t, qList, quizRepository, questionRepository);
        }

        // Topic 3: City, Buildings & Places
        if (topics.size() > 2) {
            Topic t = topics.get(2);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("What is the German word for ‘train station’?", QuestionType.MULTIPLE_CHOICE.name(), "der Bahnhof", "der Flughafen", "das Krankenhaus", "die Apotheke", "A", "‘der Bahnhof’ means train station.", 1));
            qList.add(new Question("What is the German word for ‘airport’?", QuestionType.MULTIPLE_CHOICE.name(), "der Flughafen", "der Bahnhof", "die Bibliothek", "die Schule", "A", "‘der Flughafen’ means airport (Flug = flight + Hafen = port).", 1));
            qList.add(new Question("What is the definite article for ‘Krankenhaus’ (hospital)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "das", "‘das Krankenhaus’ is neuter.", 1));
            qList.add(new Question("What is the definite article for ‘Apotheke’ (pharmacy)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "die", "Nouns ending in -e like ‘die Apotheke’ are typically feminine.", 1));
            qList.add(new Question("Translate to English: ‘das Geschäft’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "the shop / the store / shop / store", "‘das Geschäft’ means the shop or store.", 1));
            qList.add(new Question("Translate to German: ‘the library’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "die Bibliothek", "‘die Bibliothek’ is the library.", 1));
            qList.add(new Question("Which place sells medicine and health supplies?", QuestionType.MULTIPLE_CHOICE.name(), "die Apotheke", "die Bank", "das Kino", "die Post", "A", "‘die Apotheke’ is the pharmacy.", 1));
            qList.add(new Question("What is the German word for ‘movie theater / cinema’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "das Kino", "‘das Kino’ means cinema.", 1));
            qList.add(new Question("True or False: ‘die Bank’ in German can mean both a financial bank and a park bench.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! ‘die Bank’ means financial bank (plural: Banken) and bench (plural: Bänke).", 1));
            qList.add(new Question("What is the definite article for ‘Supermarkt’? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "‘der Supermarkt’ is masculine.", 1));
            qList.add(new Question("Translate to English: ‘das Rathaus’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "the town hall / town hall / city hall", "‘das Rathaus’ is town hall / city hall.", 1));
            qList.add(new Question("Translate to German: ‘the school’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "die Schule", "‘die Schule’ is the school.", 1));
            qList.add(new Question("Where do you go to mail a letter or package in Germany?", QuestionType.MULTIPLE_CHOICE.name(), "die Post", "das Museum", "das Hotel", "die Universität", "A", "‘die Post’ is the post office.", 1));
            qList.add(new Question("What is the definite article for ‘Wohnung’ (apartment)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "die", "Nouns ending in -ung are always feminine: ‘die Wohnung’.", 1));
            qList.add(new Question("Complete the sentence: ‘Wir essen im ____.’ (restaurant)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Restaurant", "‘das Restaurant’ (im Restaurant).", 1));
            saveQuestionsForTopic(mod2, t, qList, quizRepository, questionRepository);
        }

        // Topic 4: Asking & Giving Directions
        if (topics.size() > 3) {
            Topic t = topics.get(3);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("What is the German word for ‘turn left’?", QuestionType.MULTIPLE_CHOICE.name(), "Biegen Sie links ab", "Biegen Sie rechts ab", "Gehen Sie geradeaus", "Gehen Sie zurück", "A", "‘links’ = left, ‘abbiegen’ = to turn.", 1));
            qList.add(new Question("What does ‘Gehen Sie geradeaus’ mean in English?", QuestionType.MULTIPLE_CHOICE.name(), "Go straight ahead", "Turn right", "Turn left", "Go backwards", "A", "‘geradeaus’ means straight ahead.", 1));
            qList.add(new Question("What is the German word for ‘traffic light’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Ampel", "‘die Ampel’ is the traffic light.", 1));
            qList.add(new Question("What is the German word for ‘intersection / crossroads’?", QuestionType.MULTIPLE_CHOICE.name(), "die Kreuzung", "die Brücke", "der Platz", "der Kreisverkehr", "A", "‘die Kreuzung’ is the intersection.", 1));
            qList.add(new Question("Translate to German: ‘opposite / across from’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "gegenüber / gegenüber von", "‘gegenüber’ means opposite / across from.", 1));
            qList.add(new Question("Translate to English: ‘Die Bank ist neben der Post.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "The bank is next to the post office / The bank is beside the post office", "‘neben’ means next to or beside.", 1));
            qList.add(new Question("What is the German preposition for ‘between’?", QuestionType.MULTIPLE_CHOICE.name(), "zwischen", "neben", "hinter", "vor", "A", "‘zwischen’ means between.", 1));
            qList.add(new Question("Translate to German: ‘Turn right!’ (formal)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Biegen Sie rechts ab! / Biegen Sie rechts ab", "‘Biegen Sie rechts ab!’ is the formal direction command for turning right.", 1));
            qList.add(new Question("True or False: ‘rechts’ in German means ‘left’.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "False! ‘rechts’ means right, and ‘links’ means left.", 1));
            qList.add(new Question("What is the German word for ‘bridge’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Brücke", "‘die Brücke’ is the bridge.", 1));
            qList.add(new Question("Complete the question: ‘Entschuldigung, wie komme ich zum ____?’ (train station)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Bahnhof", "‘zum Bahnhof’ (zu + dem Bahnhof).", 1));
            qList.add(new Question("What does ‘Kreisverkehr’ mean in English?", QuestionType.MULTIPLE_CHOICE.name(), "Roundabout", "Crossroads", "Dead end", "Pedestrian zone", "A", "‘der Kreisverkehr’ is the roundabout / traffic circle.", 1));
            qList.add(new Question("Translate to English: ‘vor dem Hotel’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "in front of the hotel / before the hotel", "‘vor’ means in front of or before.", 1));
            qList.add(new Question("What is the definite article for ‘Platz’ (town square / plaza)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "‘der Platz’ is masculine.", 1));
            qList.add(new Question("Translate to German: ‘Where is the pharmacy?’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Wo ist die Apotheke? / Wo ist die Apotheke", "‘Wo ist die Apotheke?’ asks for the pharmacy's location.", 1));
            saveQuestionsForTopic(mod2, t, qList, quizRepository, questionRepository);
        }

        // Topic 5: Transportation & Imperative
        if (topics.size() > 4) {
            Topic t = topics.get(4);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Which preposition is used for means of transportation (e.g. by bus, by train)?", QuestionType.MULTIPLE_CHOICE.name(), "mit", "in", "an", "bei", "A", "Transportation always uses ‘mit’ + Dative: ‘mit dem Bus’, ‘mit dem Zug’.", 1));
            qList.add(new Question("Fill in the dative article: Ich fahre mit ____ Bus (der Bus).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "dem", "‘mit’ takes dative; masculine der Bus becomes ‘dem Bus’.", 1));
            qList.add(new Question("Fill in the dative article: Sie fährt mit ____ U-Bahn (die U-Bahn).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der", "Feminine die U-Bahn becomes ‘der U-Bahn’ in the dative.", 1));
            qList.add(new Question("What is the German idiom for ‘on foot’?", QuestionType.MULTIPLE_CHOICE.name(), "zu Fuß", "mit Fuß", "auf Fuß", "an Fuß", "A", "‘zu Fuß’ is the fixed German idiom for walking on foot.", 1));
            qList.add(new Question("What is the informal singular (du) imperative of ‘kommen’?", QuestionType.MULTIPLE_CHOICE.name(), "Komm!", "Kommst!", "Kommen!", "Komme!", "A", "Drop ‘du’ and the ‘-st’ ending: ‘Komm!’.", 1));
            qList.add(new Question("What is the informal singular (du) imperative of ‘fahren’?", QuestionType.MULTIPLE_CHOICE.name(), "Fahr!", "Fährst!", "Fahre!", "Fahrt!", "A", "Verbs with vowel shift a -> ä drop the Umlaut in the du-imperative: ‘Fahr!’.", 1));
            qList.add(new Question("What is the informal singular (du) imperative of ‘nehmen’ (to take)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Nimm", "Vowel shift e -> i stays in the imperative: ‘Nimm!’ (e.g. Nimm den Bus!).", 1));
            qList.add(new Question("Translate to German: ‘Take the train!’ (formal)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Nehmen Sie den Zug! / Nehmen Sie den Zug", "Formal imperative: ‘Nehmen Sie den Zug!’.", 1));
            qList.add(new Question("Translate to English: ‘Fahr bitte vorsichtig!’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "Drive carefully please / Please drive carefully", "‘Fahr vorsichtig’ means drive carefully.", 1));
            qList.add(new Question("What is the German word for ‘airplane’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "das Flugzeug", "‘das Flugzeug’ is the airplane.", 1));
            qList.add(new Question("What is the German word for ‘subway / metro’?", QuestionType.MULTIPLE_CHOICE.name(), "die U-Bahn", "die S-Bahn", "die Straßenbahn", "der Bus", "A", "‘die U-Bahn’ (Untergrundbahn) is the subway.", 1));
            qList.add(new Question("True or False: In the formal ‘Sie-Imperativ’, the pronoun ‘Sie’ is kept immediately following the verb.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! ‘Kommen Sie!’, ‘Fahren Sie!’, ‘Gehen Sie!’.", 1));
            qList.add(new Question("What is the German word for ‘bicycle’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "das Fahrrad", "‘das Fahrrad’ is the bicycle.", 1));
            qList.add(new Question("Complete the sentence: ‘Ich fahre jeden Tag mit dem ____ zur Arbeit.’ (bicycle)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Fahrrad", "‘mit dem Fahrrad’ (by bicycle).", 1));
            qList.add(new Question("What is the informal plural (ihr) imperative of ‘gehen’?", QuestionType.MULTIPLE_CHOICE.name(), "Geht!", "Geh!", "Gehen Sie!", "Gehen!", "A", "The ihr-imperative takes the regular ihr verb form without pronoun: ‘Geht!’.", 1));
            saveQuestionsForTopic(mod2, t, qList, quizRepository, questionRepository);
        }

        // Topic 6: Food, Meals & Drinks
        if (topics.size() > 5) {
            Topic t = topics.get(5);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("What is the German word for ‘breakfast’?", QuestionType.MULTIPLE_CHOICE.name(), "das Frühstück", "das Mittagessen", "das Abendessen", "der Snack", "A", "‘das Frühstück’ is breakfast.", 1));
            qList.add(new Question("What is the German word for ‘dinner / evening meal’?", QuestionType.MULTIPLE_CHOICE.name(), "das Abendessen", "das Frühstück", "das Mittagessen", "die Mahlzeit", "A", "‘das Abendessen’ (or das Abendbrot) is dinner.", 1));
            qList.add(new Question("What is the definite article for ‘Brot’ (bread)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "das", "‘das Brot’ is neuter.", 1));
            qList.add(new Question("What is the definite article for ‘Kaffee’ (coffee)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "‘der Kaffee’ is masculine.", 1));
            qList.add(new Question("Translate to English: ‘Ich trinke gern Tee mit Zitrone.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "I like to drink tea with lemon / I like drinking tea with lemon", "‘Tee mit Zitrone’ is tea with lemon.", 1));
            qList.add(new Question("Translate to German: ‘an apple’ (nominative)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "ein Apfel", "‘der Apfel’ (masculine) -> ‘ein Apfel’.", 1));
            qList.add(new Question("What is the definite article for ‘Milch’ (milk)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "die", "‘die Milch’ is feminine.", 1));
            qList.add(new Question("What is the plural of ‘der Apfel’ (the apple)?", QuestionType.MULTIPLE_CHOICE.name(), "die Äpfel", "die Apfeln", "die Apfels", "die Äpfele", "A", "‘der Apfel’ adds an Umlaut: ‘die Äpfel’.", 1));
            qList.add(new Question("What is the German word for ‘chicken meat’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "das Hähnchen", "‘das Hähnchen’ is chicken.", 1));
            qList.add(new Question("What is the definite article for ‘Wasser’ (water)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "das", "‘das Wasser’ is neuter.", 1));
            qList.add(new Question("Translate to English: ‘das Gemüse und das Obst’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "the vegetables and the fruit / vegetables and fruit", "‘das Gemüse’ = vegetables, ‘das Obst’ = fruit.", 1));
            qList.add(new Question("Complete the sentence: ‘Zum Frühstück esse ich ein ____ mit Käse.’ (bread roll)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Brötchen", "‘das Brötchen’ is bread roll.", 1));
            qList.add(new Question("True or False: ‘das Mittagessen’ in German means ‘lunch’.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! Mittag (midday) + Essen (meal) = lunch.", 1));
            qList.add(new Question("Conjugate ‘essen’ for ‘du’: Was ____ du gern?", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "isst", "‘essen’ has a vowel change: e -> i: ‘du isst’.", 1));
            qList.add(new Question("What is the definite article for ‘Käse’ (cheese)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "‘der Käse’ is masculine.", 1));
            saveQuestionsForTopic(mod2, t, qList, quizRepository, questionRepository);
        }

        // Topic 7: Family & The Accusative Case
        if (topics.size() > 6) {
            Topic t = topics.get(6);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("In the accusative case, what does the masculine definite article ‘der’ change into?", QuestionType.MULTIPLE_CHOICE.name(), "den", "dem", "des", "die", "A", "Masculine 'der' changes to 'den' in the accusative case. Feminine and neuter stay unchanged.", 1));
            qList.add(new Question("Fill in the accusative article: Ich besuche ____ Vater (der Vater).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "den", "‘der Vater’ becomes ‘den Vater’ in the accusative.", 1));
            qList.add(new Question("Fill in the possessive: Ich liebe ____ Mutter (die Mutter).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "meine", "Feminine accusative possessive is ‘meine Mutter’.", 1));
            qList.add(new Question("What is the German word for ‘parents’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Eltern", "‘die Eltern’ means parents (plural).", 1));
            qList.add(new Question("Translate to German: ‘I have a brother.’ (accusative)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich habe einen Bruder", "‘der Bruder’ in the accusative becomes ‘einen Bruder’.", 1));
            qList.add(new Question("Translate to English: ‘Das ist meine Schwester.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "This is my sister / That is my sister", "‘die Schwester’ means sister.", 1));
            qList.add(new Question("What is the German word for ‘grandfather’?", QuestionType.MULTIPLE_CHOICE.name(), "der Großvater", "der Großmutter", "der Onkel", "der Cousin", "A", "‘der Großvater’ (or Opa) is grandfather.", 1));
            qList.add(new Question("What is the German word for ‘daughter’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Tochter", "‘die Tochter’ is daughter.", 1));
            qList.add(new Question("What is the German word for ‘son’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Sohn", "‘der Sohn’ is son.", 1));
            qList.add(new Question("Fill in the indefinite article: Er hat ____ Schwester (die Schwester, accusative).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "eine", "Feminine accusative indefinite article remains ‘eine’.", 1));
            qList.add(new Question("Fill in the indefinite article: Wir haben ____ Kind (das Kind, accusative).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "ein", "Neuter accusative indefinite article remains ‘ein’.", 1));
            qList.add(new Question("True or False: In German, only masculine articles change their form in the accusative case (der -> den, ein -> einen).", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! Feminine (die/eine), neuter (das/ein), and plural (die) do not change.", 1));
            qList.add(new Question("Translate to German: ‘my uncle’ (nominative)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "mein Onkel", "‘der Onkel’ -> ‘mein Onkel’.", 1));
            qList.add(new Question("Complete the sentence: ‘Ich rufe meinen ____ an.’ (brother, accusative)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Bruder", "‘meinen Bruder’ (accusative).", 1));
            qList.add(new Question("What is the German word for ‘aunt’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Tante", "‘die Tante’ is aunt.", 1));
            saveQuestionsForTopic(mod2, t, qList, quizRepository, questionRepository);
        }
    }

    // =========================================================================
    // MODULE 3 QUESTION BANKS (7 Topics)
    // =========================================================================
    private void seedModule3Questions(Module mod3, QuizRepository quizRepository, QuestionRepository questionRepository) {
        List<Topic> topics = mod3.getTopics();
        if (topics.isEmpty()) return;

        // Topic 1: Uhrzeit & Termine
        if (topics.size() > 0) {
            Topic t = topics.get(0);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("In German time-telling, what does ‘halb acht’ (half eight) mean?", QuestionType.MULTIPLE_CHOICE.name(), "7:30 (half an hour before eight)", "8:30 (half an hour after eight)", "8:00", "7:45", "A", "In German, ‘halb acht’ means half-way to 8:00, which is 7:30.", 1));
            qList.add(new Question("How do you say 8:15 in German informal time?", QuestionType.MULTIPLE_CHOICE.name(), "Viertel nach acht", "Viertel vor acht", "halb neun", "acht Uhr fünfzehn", "A", "8:15 is ‘Viertel nach acht’ (quarter past eight).", 1));
            qList.add(new Question("How do you say 8:45 in German informal time?", QuestionType.MULTIPLE_CHOICE.name(), "Viertel vor neun", "Viertel nach acht", "halb neun", "neun Uhr fünfundvierzig", "A", "8:45 is ‘Viertel vor neun’ (quarter to nine).", 1));
            qList.add(new Question("Translate to German: ‘What time is it?’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Wie spät ist es? / Wie viel Uhr ist es?", "Both ‘Wie spät ist es?’ and ‘Wie viel Uhr ist es?’ are standard.", 1));
            qList.add(new Question("What is the German word for ‘appointment’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Termin", "‘der Termin’ is appointment.", 1));
            qList.add(new Question("Fill in the blank: Ich möchte einen Termin ____ (to schedule / arrange).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "vereinbaren", "‘einen Termin vereinbaren’ means to make/schedule an appointment.", 1));
            qList.add(new Question("What does ‘einen Termin absagen’ mean in English?", QuestionType.MULTIPLE_CHOICE.name(), "To cancel an appointment", "To postpone an appointment", "To confirm an appointment", "To arrive late", "A", "‘absagen’ means to cancel.", 1));
            qList.add(new Question("Translate to English: ‘Der Zug hat 10 Minuten Verspätung.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "The train is 10 minutes late / The train has a 10 minute delay", "‘Verspätung haben’ means to have a delay.", 1));
            qList.add(new Question("True or False: In official German time (trains, flights), 16:30 is spoken as ‘sechzehn Uhr dreißig’.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! Official time uses the 24-hour format: 'sechzehn Uhr dreißig'.", 1));
            qList.add(new Question("What is the German word for ‘delay’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Verspätung", "‘die Verspätung’ is delay.", 1));
            qList.add(new Question("Translate to German: ‘to postpone an appointment’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "einen Termin verschieben / verschieben", "‘verschieben’ means to postpone/reschedule.", 1));
            qList.add(new Question("What time is ‘zehn vor zehn’ in numbers?", QuestionType.MULTIPLE_CHOICE.name(), "9:50", "10:10", "10:50", "9:10", "A", "‘zehn vor zehn’ is ten minutes before ten, i.e., 9:50.", 1));
            qList.add(new Question("Complete the sentence: ‘Um wie viel ____ treffen wir uns?’ (o'clock)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Uhr", "‘Um wie viel Uhr’ means at what time.", 1));
            qList.add(new Question("What is the definite article for ‘Fahrkarte’ (ticket)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "die", "Nouns ending in -e are feminine: ‘die Fahrkarte’.", 1));
            qList.add(new Question("Translate to English: ‘halb drei’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "2:30 / two thirty / half past two", "‘halb drei’ is half an hour before three, i.e., 2:30.", 1));
            saveQuestionsForTopic(mod3, t, qList, quizRepository, questionRepository);
        }

        // Topic 2: Zeitpräpositionen
        if (topics.size() > 1) {
            Topic t = topics.get(1);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Which preposition means ‘from ... to ...’ for a time span in German?", QuestionType.MULTIPLE_CHOICE.name(), "von ... bis", "ab ... bis", "seit ... bis", "an ... in", "A", "‘von ... bis’ expresses from a start time to an end time.", 1));
            qList.add(new Question("Fill in the preposition: Ich arbeite ____ 9 Uhr bis 17 Uhr.", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "von", "‘von 9 Uhr bis 17 Uhr’ (from 9 to 5).", 1));
            qList.add(new Question("Which preposition expresses an ongoing action that started in the past (equivalent to English 'since' or 'for')?", QuestionType.MULTIPLE_CHOICE.name(), "seit", "ab", "vor", "nach", "A", "‘seit’ + Dative expresses ongoing actions (e.g. Ich lerne seit einem Jahr Deutsch).", 1));
            qList.add(new Question("Fill in the preposition: Der Kurs beginnt ____ morgen (starting from tomorrow).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "ab", "‘ab’ indicates starting from a future point in time.", 1));
            qList.add(new Question("Translate to English: ‘Ich wohne seit zwei Jahren in Berlin.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "I have been living in Berlin for two years / I live in Berlin since two years", "‘seit’ with present tense translates to English present perfect continuous.", 1));
            qList.add(new Question("Which preposition means ‘around / approximately’ for time in German?", QuestionType.MULTIPLE_CHOICE.name(), "gegen", "um", "an", "bei", "A", "‘gegen’ means around/approx (e.g. gegen 18 Uhr = around 6 PM).", 1));
            qList.add(new Question("Fill in the preposition: Wir treffen uns ____ 15 Uhr (exact time).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "um", "Exact clock times use ‘um’ (um 15 Uhr).", 1));
            qList.add(new Question("True or False: ‘seit’ in German is used with the present tense to describe an ongoing action.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! German uses present tense + seit (Ich lerne seit 6 Monaten).", 1));
            qList.add(new Question("Translate to German: ‘from Monday to Friday’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "von Montag bis Freitag", "‘von Montag bis Freitag’.", 1));
            qList.add(new Question("Fill in the preposition: ____ dem Essen trinken wir Kaffee (after the meal).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Nach", "‘nach’ means after.", 1));
            qList.add(new Question("Fill in the preposition: ____ dem Unterricht wiederhole ich die Vokabeln (before class).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Vor", "‘vor’ means before.", 1));
            qList.add(new Question("Translate to English: ‘ab nächster Woche’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "starting next week / from next week", "‘ab’ means starting from.", 1));
            qList.add(new Question("Which case does the preposition ‘seit’ always require?", QuestionType.MULTIPLE_CHOICE.name(), "Dative", "Accusative", "Genitive", "Nominative", "A", "‘seit’ is always a Dative preposition.", 1));
            qList.add(new Question("Translate to German: ‘around 8 o'clock’ (approximate)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "gegen 8 Uhr / gegen acht Uhr", "‘gegen 8 Uhr’ means around 8 o'clock.", 1));
            qList.add(new Question("Complete the sentence: ‘Die Praxis ist von 8 ____ 12 Uhr geöffnet.’", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "bis", "‘von ... bis’ (from ... to).", 1));
            saveQuestionsForTopic(mod3, t, qList, quizRepository, questionRepository);
        }

        // Topic 3: Modalverben
        if (topics.size() > 2) {
            Topic t = topics.get(2);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Conjugate ‘können’ for ‘ich’: Ich ____ gut Deutsch sprechen.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "kann", "Modal verbs change vowel in singular: ‘ich kann’ and ‘er kann’.", 1));
            qList.add(new Question("Where does the infinitive main verb go in a sentence with a modal verb?", QuestionType.MULTIPLE_CHOICE.name(), "At the very end of the sentence", "Immediately after the modal verb", "Position 1", "Before the subject", "A", "In the modal verb bracket structure, the conjugated modal is in Position 2 and the infinitive goes to the very end.", 1));
            qList.add(new Question("Conjugate ‘müssen’ for ‘er’: Er ____ heute lange arbeiten.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "muss", "‘müssen’ singular: ich muss, du musst, er muss.", 1));
            qList.add(new Question("What does ‘wollen’ express in German?", QuestionType.MULTIPLE_CHOICE.name(), "Intention or desire (to want)", "Ability (to be able to)", "Permission (to be allowed to)", "Obligation (to must)", "A", "‘wollen’ means to want / to intend.", 1));
            qList.add(new Question("Conjugate ‘wollen’ for ‘du’: Was ____ du essen?", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "willst", "‘wollen’ singular: du willst.", 1));
            qList.add(new Question("Translate to German: ‘Can you help me? (formal)’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Können Sie mir helfen? / Können Sie mir helfen", "‘Können Sie mir helfen?’ uses können + infinitive at the end.", 1));
            qList.add(new Question("True or False: In German modal verbs, the 1st person (ich) and 3rd person (er/sie/es) singular forms are always identical.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! (ich kann = er kann; ich muss = er muss; ich will = er will).", 1));
            qList.add(new Question("Translate to English: ‘Wir müssen einen Termin vereinbaren.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "We must make an appointment / We have to schedule an appointment", "‘müssen’ means must / have to.", 1));
            qList.add(new Question("Conjugate ‘können’ for ‘ihr’: ____ ihr Deutsch sprechen?", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "Könnt", "Plural form for ihr is ‘könnt’.", 1));
            qList.add(new Question("Fill in the blank: Ich ____ einen Kaffee trinken (möchten - ich form).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "möchte", "‘ich möchte’ expresses polite desire (I would like).", 1));
            qList.add(new Question("Complete the sentence: ‘Sie kann sehr gut Klavier ____.’ (to play)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "spielen", "The main verb goes to the end in the infinitive: ‘spielen’.", 1));
            qList.add(new Question("Conjugate ‘müssen’ for ‘wir’: Wir ____ jetzt gehen.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "müssen", "wir form is regular with infinitive: ‘wir müssen’.", 1));
            qList.add(new Question("Translate to German: ‘I want to travel to Germany.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich will nach Deutschland reisen / Ich möchte nach Deutschland reisen", "‘wollen’ + destination + reisen at the end.", 1));
            qList.add(new Question("What does ‘nicht dürfen’ express in German?", QuestionType.MULTIPLE_CHOICE.name(), "Strict prohibition (not allowed to)", "Lack of necessity (don't have to)", "Inability (cannot)", "Dislike", "A", "‘nicht dürfen’ means strict prohibition: you are not allowed to.", 1));
            qList.add(new Question("Conjugate ‘wollen’ for ‘er’: Er ____ Arzt werden.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "will", "‘er will’ (3rd person singular of wollen).", 1));
            saveQuestionsForTopic(mod3, t, qList, quizRepository, questionRepository);
        }

        // Topic 4: Berufe & Arbeitsplatz
        if (topics.size() > 3) {
            Topic t = topics.get(3);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("How do you form the feminine version of most German professions (e.g. der Lehrer)?", QuestionType.MULTIPLE_CHOICE.name(), "Add -in (die Lehrerin)", "Add -e (die Lehre)", "Add -ung (die Lehrung)", "No change", "A", "Feminine professions add -in: ‘der Lehrer’ -> ‘die Lehrerin’.", 1));
            qList.add(new Question("What is the German word for ‘doctor’ (male)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Arzt", "‘der Arzt’ is doctor (male); ‘die Ärztin’ is doctor (female).", 1));
            qList.add(new Question("Which preposition is used to state one's job (e.g. I work as an engineer)?", QuestionType.MULTIPLE_CHOICE.name(), "als", "wie", "für", "bei", "A", "German uses ‘als’ for professions: ‘Ich arbeite als Ingenieur’.", 1));
            qList.add(new Question("Which preposition is used for the employer/company (e.g. I work at BMW)?", QuestionType.MULTIPLE_CHOICE.name(), "bei", "an", "in", "zu", "A", "Working at a company uses ‘bei’: ‘Ich arbeite bei BMW’.", 1));
            qList.add(new Question("What is the German word for ‘workplace / office’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "das Büro", "‘das Büro’ is the office.", 1));
            qList.add(new Question("Translate to English: ‘Was sind Sie von Beruf?’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "What is your profession? / What do you do for a living?", "‘Was sind Sie von Beruf?’ asks for someone's occupation.", 1));
            qList.add(new Question("Translate to German: ‘She works as a teacher.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Sie arbeitet als Lehrerin", "‘Sie arbeitet als Lehrerin’ (feminine profession).", 1));
            qList.add(new Question("What is the German word for ‘colleague’ (male)?", QuestionType.MULTIPLE_CHOICE.name(), "der Kollege", "der Chef", "der Verkäufer", "der Koch", "A", "‘der Kollege’ is colleague (female: die Kollegin).", 1));
            qList.add(new Question("What is the German word for ‘cook / chef’ (male)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Koch", "‘der Koch’ is cook/chef (female: die Köchin).", 1));
            qList.add(new Question("What is the German word for ‘salary / pay’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "das Gehalt", "‘das Gehalt’ is salary.", 1));
            qList.add(new Question("True or False: In German, you say ‘Ich bin ein Arzt’ with the indefinite article when stating your profession.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "In German, you omit the article when stating profession: ‘Ich bin Arzt’ (not *ein Arzt).", 1));
            qList.add(new Question("What is the German word for ‘salesperson’ (male)?", QuestionType.MULTIPLE_CHOICE.name(), "der Verkäufer", "der Kellner", "der Fahrer", "der Handwerker", "A", "‘der Verkäufer’ is salesperson (female: die Verkäuferin).", 1));
            qList.add(new Question("Complete the sentence: ‘Ich arbeite ____ Software-Entwickler.’ (as)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "als", "‘als’ expresses job role.", 1));
            qList.add(new Question("Translate to English: ‘die Bewerbung’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "the job application / the application / application", "‘die Bewerbung’ is job application.", 1));
            qList.add(new Question("What is the definite article for ‘Beruf’ (profession)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "‘der Beruf’ is masculine.", 1));
            saveQuestionsForTopic(mod3, t, qList, quizRepository, questionRepository);
        }

        // Topic 5: Gesundheit, Körperteile & Gefühle
        if (topics.size() > 4) {
            Topic t = topics.get(4);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("How do you say ‘My head hurts / I have a headache’ in German?", QuestionType.MULTIPLE_CHOICE.name(), "Mein Kopf tut weh", "Mein Kopf ist krank", "Ich habe Kopfweh schmerzen", "Mein Kopf tut gut", "A", "‘Mein Kopf tut weh’ or ‘Ich habe Kopfschmerzen’ expresses headache.", 1));
            qList.add(new Question("What is the German word for ‘stomach / belly’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Bauch", "‘der Bauch’ is stomach/belly.", 1));
            qList.add(new Question("What is the German word for ‘back’ (body part)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Rücken", "‘der Rücken’ is the back.", 1));
            qList.add(new Question("What German wish do you say to someone who is ill (equivalent to 'Get well soon!')?", QuestionType.MULTIPLE_CHOICE.name(), "Gute Besserung!", "Guten Appetit!", "Herzlichen Glückwunsch!", "Viel Glück!", "A", "‘Gute Besserung!’ means 'Get well soon!'.", 1));
            qList.add(new Question("Translate to German: ‘I am sick.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich bin krank", "‘krank’ means sick/ill.", 1));
            qList.add(new Question("Translate to English: ‘Ich habe Fieber und Husten.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "I have fever and a cough / I have fever and cough", "‘das Fieber’ = fever, ‘der Husten’ = cough.", 1));
            qList.add(new Question("What is the plural of ‘das Auge’ (the eye)?", QuestionType.MULTIPLE_CHOICE.name(), "die Augen", "die Auge", "die Auges", "die Äuger", "A", "‘das Auge’ -> ‘die Augen’.", 1));
            qList.add(new Question("What is the plural of ‘die Hand’ (the hand)?", QuestionType.MULTIPLE_CHOICE.name(), "die Hände", "die Handen", "die Hands", "die Händer", "A", "‘die Hand’ -> ‘die Hände’ (Umlaut + -e).", 1));
            qList.add(new Question("What is the German word for ‘tired’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "müde", "‘müde’ means tired.", 1));
            qList.add(new Question("What is the German word for ‘healthy’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "gesund", "‘gesund’ means healthy.", 1));
            qList.add(new Question("True or False: ‘Mein Bein tut weh’ uses the plural verb form ‘tun’ because Bein is neuter.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "False! Singular subject 'Mein Bein' takes singular verb ‘tut weh’. Plural subject 'Meine Beine' takes ‘tun weh’.", 1));
            qList.add(new Question("What is the German word for ‘pain medication / painkiller’?", QuestionType.MULTIPLE_CHOICE.name(), "die Schmerztablette", "der Hustensaft", "das Pflaster", "der Verband", "A", "‘die Schmerztablette’ is painkiller tablet.", 1));
            qList.add(new Question("Translate to German: ‘My feet hurt.’ (plural)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Meine Füße tun weh", "‘die Füße’ (plural) takes ‘tun weh’.", 1));
            qList.add(new Question("Complete the phrase: ‘Ich habe eine Erkältung / Ich bin ____.’ (cold/caught a cold)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "erkältet", "‘erkältet sein’ means to have a cold.", 1));
            qList.add(new Question("What is the definite article for ‘Hals’ (throat/neck)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "‘der Hals’ is masculine.", 1));
            saveQuestionsForTopic(mod3, t, qList, quizRepository, questionRepository);
        }

        // Topic 6: Das Perfekt
        if (topics.size() > 5) {
            Topic t = topics.get(5);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Which auxiliary verb is used to form the Perfekt for regular transitive verbs like ‘arbeiten’ and ‘lernen’?", QuestionType.MULTIPLE_CHOICE.name(), "haben", "sein", "werden", "machen", "A", "Most verbs (and all transitive verbs) use ‘haben’ as the Perfekt auxiliary.", 1));
            qList.add(new Question("Which auxiliary verb is used for verbs of motion and change of state (e.g. gehen, fahren, kommen)?", QuestionType.MULTIPLE_CHOICE.name(), "sein", "haben", "werden", "tun", "A", "Verbs expressing movement or change of state use ‘sein’ (Ich bin gegangen).", 1));
            qList.add(new Question("What is the Partizip II (past participle) of ‘machen’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "gemacht", "Regular verb formula: ge- + mach + -t = ‘gemacht’.", 1));
            qList.add(new Question("What is the Partizip II of ‘lernen’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "gelernt", "Regular verb: ge- + lern + -t = ‘gelernt’.", 1));
            qList.add(new Question("What is the Partizip II of ‘gehen’ (to go)?", QuestionType.MULTIPLE_CHOICE.name(), "gegangen", "gegeht", "gegangt", "gegengen", "A", "Irregular verb: ‘gehen’ -> ‘gegangen’ (with sein: Ich bin gegangen).", 1));
            qList.add(new Question("What is the Partizip II of ‘essen’ (to eat)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "gegessen", "‘essen’ -> ‘gegessen’ (Ich habe gegessen).", 1));
            qList.add(new Question("Translate to German: ‘I have worked.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich habe gearbeitet", "‘arbeiten’ -> ge-arbeit-et with haben.", 1));
            qList.add(new Question("Translate to English: ‘Er ist nach Berlin gefahren.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "He drove to Berlin / He went to Berlin / He has driven to Berlin", "‘fahren’ is motion, takes ‘sein’: ‘ist gefahren’.", 1));
            qList.add(new Question("Where does the Partizip II go in a standard German main clause?", QuestionType.MULTIPLE_CHOICE.name(), "At the very end of the sentence", "Position 2", "Position 1", "Next to the auxiliary", "A", "The auxiliary is at Position 2 and the Partizip II is anchored at the end.", 1));
            qList.add(new Question("Fill in the auxiliary verb: Ich ____ gestern Fußball gespielt (haben/sein).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "habe", "‘spielen’ takes haben: ‘Ich habe gespielt’.", 1));
            qList.add(new Question("Fill in the auxiliary verb: Wir ____ nach Hause gegangen (haben/sein).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "sind", "‘gehen’ is motion, takes sein: ‘Wir sind gegangen’.", 1));
            qList.add(new Question("What is the Partizip II of ‘sehen’ (to see)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "gesehen", "‘sehen’ -> ‘gesehen’.", 1));
            qList.add(new Question("True or False: Verbs ending in ‘-ieren’ (like studieren, reservieren) do NOT take the ‘ge-’ prefix in Partizip II (e.g. studiert).", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! Verbs in -ieren form Partizip II without ge-: studiert, reserviert, telefoniert.", 1));
            qList.add(new Question("What is the Partizip II of ‘bleiben’ (to stay)?", QuestionType.MULTIPLE_CHOICE.name(), "geblieben", "gebleibt", "gebleiben", "bleibt", "A", "‘bleiben’ -> ‘geblieben’ (takes sein: Ich bin geblieben).", 1));
            qList.add(new Question("Complete the sentence: ‘Hast du deine Hausaufgaben ____?’ (to do / gemacht)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "gemacht", "Partizip II goes to the end: ‘gemacht’.", 1));
            saveQuestionsForTopic(mod3, t, qList, quizRepository, questionRepository);
        }

        // Topic 7: Reisen & Hotel
        if (topics.size() > 6) {
            Topic t = topics.get(6);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("What is the German word for a ‘single room’ in a hotel?", QuestionType.MULTIPLE_CHOICE.name(), "das Einzelzimmer", "das Doppelzimmer", "die Suite", "das Schlafzimmer", "A", "‘das Einzelzimmer’ is single room; ‘das Doppelzimmer’ is double room.", 1));
            qList.add(new Question("What is the German word for a ‘double room’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "das Doppelzimmer", "‘das Doppelzimmer’ is double room.", 1));
            qList.add(new Question("Translate to German: ‘I would like to book a room.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich möchte ein Zimmer buchen / Ich möchte ein Zimmer reservieren", "‘ein Zimmer buchen / reservieren’ means to book a room.", 1));
            qList.add(new Question("What does ‘Frühstück inklusive’ mean on a hotel booking?", QuestionType.MULTIPLE_CHOICE.name(), "Breakfast included", "No breakfast", "Breakfast costs extra", "Lunch included", "A", "‘inklusive’ means included.", 1));
            qList.add(new Question("What is the German word for ‘luggage / baggage’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "das Gepäck", "‘das Gepäck’ is luggage.", 1));
            qList.add(new Question("What is the German word for ‘suitcase’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Koffer", "‘der Koffer’ is suitcase.", 1));
            qList.add(new Question("What is the German word for ‘train platform / track’?", QuestionType.MULTIPLE_CHOICE.name(), "der Bahnsteig", "das Gleis", "der Bahnhof", "die Haltestelle", "A", "‘der Bahnsteig’ (or das Gleis) is train platform/track.", 1));
            qList.add(new Question("Translate to English: ‘Abfahrt und Ankunft’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "Departure and arrival / Departure and Arrival", "‘die Abfahrt’ = departure, ‘die Ankunft’ = arrival.", 1));
            qList.add(new Question("What is the German word for ‘hotel reception’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Rezeption", "‘die Rezeption’ is hotel reception.", 1));
            qList.add(new Question("Translate to German: ‘The train arrives at platform 4.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Der Zug kommt auf Gleis 4 an / Der Zug kommt an Gleis 4 an", "‘ankommen auf Gleis 4’ expresses platform arrival.", 1));
            qList.add(new Question("True or False: ‘die Fahrkarte’ means train ticket / transit ticket in German.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! ‘die Fahrkarte’ or ‘das Ticket’ is transit ticket.", 1));
            qList.add(new Question("Complete the sentence: ‘Haben Sie ein Zimmer ____?’ (free/available)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "frei", "‘ein Zimmer frei haben’ means to have a room available.", 1));
            qList.add(new Question("What is the definite article for ‘Hotel’? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "das", "‘das Hotel’ is neuter.", 1));
            qList.add(new Question("Translate to English: ‘Gute Reise!’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "Have a good trip! / Have a safe trip! / Bon voyage!", "‘Gute Reise!’ means have a good trip.", 1));
            qList.add(new Question("Conjugate ‘reisen’ for ‘wir’: Wir ____ nach Deutschland.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "reisen", "‘wir reisen’ (we travel).", 1));
            saveQuestionsForTopic(mod3, t, qList, quizRepository, questionRepository);
        }
    }

    // =========================================================================
    // MODULE 4 QUESTION BANKS (6 Topics)
    // =========================================================================
    private void seedModule4Questions(Module mod4, QuizRepository quizRepository, QuestionRepository questionRepository) {
        List<Topic> topics = mod4.getTopics();
        if (topics.isEmpty()) return;

        // Topic 1: Trennbare Verben
        if (topics.size() > 0) {
            Topic t = topics.get(0);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Where does the separable prefix go in a standard present-tense main clause?", QuestionType.MULTIPLE_CHOICE.name(), "At the very end of the sentence", "Immediately before the conjugated verb", "At the very beginning", "Next to the subject", "A", "In a German main clause (Präsens), the conjugated stem sits in position 2 and the separable prefix moves to the very end.", 1));
            qList.add(new Question("Fill in the separable prefix: Ich stehe jeden Tag um 7 Uhr ____ (aufstehen).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "auf", "‘aufstehen’ splits into ‘stehe ... auf’ in a present tense main clause.", 1));
            qList.add(new Question("Fill in the separable prefix: Er kauft im Supermarkt ____ (einkaufen).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "ein", "‘einkaufen’ splits into ‘kauft ... ein’.", 1));
            qList.add(new Question("Complete the du-imperative sentence: ‘Komm bitte mit uns ____!’ (mitkommen)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "mit", "‘mitkommen’ splits into ‘Komm ... mit!’ in imperative sentences.", 1));
            qList.add(new Question("Conjugate ‘anrufen’ for ‘ich’: Ich ____ dich heute Abend an.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "rufe", "‘anrufen’ -> ‘Ich rufe ... an’.", 1));
            qList.add(new Question("Translate to German: ‘We are going shopping today.’ (einkaufen)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Wir kaufen heute ein", "‘einkaufen’ splits: ‘Wir kaufen heute ein’.", 1));
            qList.add(new Question("Translate to English: ‘Wann fängt der Film an?’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "When does the movie start? / When does the film begin?", "‘anfangen’ means to start/begin.", 1));
            qList.add(new Question("What is the Partizip II form of ‘aufstehen’ in the Perfekt tense?", QuestionType.MULTIPLE_CHOICE.name(), "aufgestanden", "geaufstanden", "aufgesteht", "aufstanden", "A", "In separable verbs, ‘-ge-’ is inserted between the prefix and the stem: ‘auf-ge-standen’.", 1));
            qList.add(new Question("What is the Partizip II of ‘einkaufen’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "eingekauft", "Separable: ‘ein-ge-kauft’.", 1));
            qList.add(new Question("True or False: In a sentence with a modal verb (e.g. Ich will...), the separable verb does NOT split and stays in the infinitive at the end.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! ‘Ich will um 7 Uhr aufstehen’ (infinitive does not split).", 1));
            qList.add(new Question("Fill in the prefix: Wann steigst du in den Zug ____? (einsteigen = to board/get on)", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "ein", "‘einsteigen’ splits into ‘steigst ... ein’.", 1));
            qList.add(new Question("What does ‘umsteigen’ mean in transit contexts?", QuestionType.MULTIPLE_CHOICE.name(), "To transfer / change trains", "To get on", "To get off", "To miss the train", "A", "‘umsteigen’ means to transfer / change trains or buses.", 1));
            qList.add(new Question("Translate to German: ‘I invite you.’ (einladen)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich lade dich ein", "‘einladen’ splits: ‘Ich lade dich ein’.", 1));
            qList.add(new Question("Conjugate ‘fernsehen’ for ‘wir’: Wir ____ am Abend fern.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "sehen", "‘wir sehen ... fern’.", 1));
            qList.add(new Question("Complete the sentence: ‘Mach bitte das Fenster ____!’ (to close / zumachen)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "zu", "‘zumachen’ splits into ‘Mach ... zu!’.", 1));
            saveQuestionsForTopic(mod4, t, qList, quizRepository, questionRepository);
        }

        // Topic 2: Der Imperativ
        if (topics.size() > 1) {
            Topic t = topics.get(1);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("True or False: In the informal ‘du-Imperativ’, you drop both the pronoun ‘du’ and the ending ‘-st’ (e.g. ‘Komm!’).", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "The informal singular imperative is formed by taking the du-form, removing ‘-st’, and dropping ‘du’: du kommst -> Komm!", 1));
            qList.add(new Question("What is the informal singular (du) imperative of ‘lesen’ (to read)?", QuestionType.MULTIPLE_CHOICE.name(), "Lies!", "Lese!", "Liest!", "Lesen!", "A", "Irregular e -> ie vowel shift is kept in the du-imperative: ‘Lies!’.", 1));
            qList.add(new Question("What is the informal plural (ihr) imperative of ‘machen’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Macht", "The ihr-imperative takes the regular ihr form without pronoun: ‘Macht!’.", 1));
            qList.add(new Question("What is the formal (Sie) imperative of ‘sprechen’?", QuestionType.MULTIPLE_CHOICE.name(), "Sprechen Sie!", "Sprich Sie!", "Sprecht Sie!", "Sprechen!", "A", "Formal imperative inverts the verb and ‘Sie’: ‘Sprechen Sie!’.", 1));
            qList.add(new Question("What is the irregular imperative of ‘sein’ for the du-form?", QuestionType.MULTIPLE_CHOICE.name(), "Sei!", "Bist!", "Sein!", "Seid!", "A", "‘sein’ has an irregular du-imperative: ‘Sei ruhig!’ (Be quiet!).", 1));
            qList.add(new Question("Translate to German: ‘Help me, please!’ (informal du)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Hilf mir bitte! / Hilf mir bitte", "‘helfen’ vowel shift e -> i: ‘Hilf mir bitte!’.", 1));
            qList.add(new Question("Translate to English: ‘Warten Sie bitte einen Moment!’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "Please wait a moment! / Wait a moment please", "Formal imperative request: 'Please wait a moment!'.", 1));
            qList.add(new Question("What is the imperative of ‘sein’ for formal ‘Sie’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Seien Sie", "Formal imperative of sein: ‘Seien Sie bitte pünktlich!’.", 1));
            qList.add(new Question("True or False: Verbs with an a -> ä vowel change (like fahren, schlafen) KEEP the Umlaut in the du-imperative.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "False! Verbs with a -> ä lose the Umlaut in the imperative: ‘Fahr!’ (not *Fähr!), ‘Schlaf!’.", 1));
            qList.add(new Question("What is the informal singular (du) imperative of ‘geben’ (to give)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Gib", "‘geben’ changes e -> i: ‘Gib mir das Buch!’.", 1));
            qList.add(new Question("Complete the command: ‘____ bitte leise!’ (ihr form of sein / to be quiet)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Seid", "ihr imperative of sein is ‘Seid!’.", 1));
            qList.add(new Question("Translate to German: ‘Eat your vegetables!’ (informal du)", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Iss dein Gemüse! / Iss dein Gemüse", "‘essen’ du-imperative is ‘Iss!’.", 1));
            qList.add(new Question("What particle is added in German to make commands polite and natural?", QuestionType.MULTIPLE_CHOICE.name(), "bitte / mal", "sehr", "doch nicht", "gern", "A", "‘bitte’ or modal particle ‘mal’ softens commands.", 1));
            qList.add(new Question("What is the informal singular (du) imperative of ‘schreiben’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Schreib", "‘schreiben’ -> ‘Schreib!’.", 1));
            qList.add(new Question("Complete the formal command: ‘____ Sie Platz!’ (nehmen / to take a seat)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Nehmen", "‘Nehmen Sie Platz!’ means take a seat.", 1));
            saveQuestionsForTopic(mod4, t, qList, quizRepository, questionRepository);
        }

        // Topic 3: Modalverben Vertiefung
        if (topics.size() > 2) {
            Topic t = topics.get(2);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("What does the modal verb ‘dürfen’ express in affirmative sentences?", QuestionType.MULTIPLE_CHOICE.name(), "Permission (to be allowed to)", "Strict obligation", "Physical ability", "Wish", "A", "‘dürfen’ expresses permission: to be allowed / permitted to.", 1));
            qList.add(new Question("Conjugate ‘dürfen’ for ‘man’: Hier ____ man nicht parken.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "darf", "‘dürfen’ singular vowel shift: ich darf, du darfst, er/man darf.", 1));
            qList.add(new Question("What does the modal verb ‘sollen’ express?", QuestionType.MULTIPLE_CHOICE.name(), "Duty, recommendation, or advice (should / ought to)", "Permission", "Ability", "Desire", "A", "‘sollen’ expresses advice, doctor's recommendation, or duty (should).", 1));
            qList.add(new Question("Conjugate ‘sollen’ for ‘du’: Du ____ mehr Wasser trinken.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "sollst", "‘sollen’ does NOT change vowel in singular: ich soll, du sollst, er soll.", 1));
            qList.add(new Question("Translate to German: ‘You are not allowed to smoke here.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Hier darf man nicht rauchen / Hier dürfen Sie nicht rauchen", "‘nicht dürfen’ expresses prohibition.", 1));
            qList.add(new Question("Translate to English: ‘Der Arzt sagt, ich soll im Bett bleiben.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "The doctor says I should stay in bed", "‘sollen’ conveys the doctor's instructions.", 1));
            qList.add(new Question("Conjugate ‘dürfen’ for ‘ich’: ____ ich das Fenster öffnen?", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "Darf", "‘Darf ich ... ?’ asks for polite permission.", 1));
            qList.add(new Question("True or False: Unlike können and müssen, the verb ‘sollen’ has NO vowel change in the singular (ich soll, du sollst, er soll).", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! ‘sollen’ keeps the vowel ‘o’ in all forms.", 1));
            qList.add(new Question("Conjugate ‘dürfen’ for ‘ihr’: ____ ihr heute ausgehen?", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "Dürft", "Plural ihr form of dürfen is ‘dürft’.", 1));
            qList.add(new Question("Translate to German: ‘What should I do?’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Was soll ich tun? / Was soll ich machen?", "‘Was soll ich tun/machen?’.", 1));
            qList.add(new Question("Conjugate ‘mögen’ for ‘ich’: Ich ____ Schokolade.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "mag", "‘mögen’ singular: ich mag, du magst, er mag.", 1));
            qList.add(new Question("Fill in the modal verb: Kinder ____ hier kostenlos spielen (dürfen).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "dürfen", "Plural form: ‘Kinder dürfen’.", 1));
            qList.add(new Question("Complete the sentence: ‘Soll ich das Fenster ____?’ (to open)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "öffnen", "Infinitive goes to the end with modal verb.", 1));
            qList.add(new Question("Translate to English: ‘Wir dürfen hier schwimmen.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "We are allowed to swim here / We can swim here", "‘dürfen’ means allowed to.", 1));
            qList.add(new Question("Conjugate ‘mögen’ for ‘du’: ____ du Pizza?", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "Magst", "‘du magst’.", 1));
            saveQuestionsForTopic(mod4, t, qList, quizRepository, questionRepository);
        }

        // Topic 4: Wetter & Jahreszeiten
        if (topics.size() > 3) {
            Topic t = topics.get(3);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("Translate to German: ‘The sun is shining.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Die Sonne scheint", "‘Die Sonne scheint’ is the German expression for sunshine (scheinen = to shine).", 1));
            qList.add(new Question("Translate to English: ‘Es regnet heute.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "It is raining today / It rains today", "‘Es regnet’ is the impersonal expression for ‘it is raining’.", 1));
            qList.add(new Question("What is the German word for ‘snow’ (noun)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Schnee", "‘der Schnee’ is snow.", 1));
            qList.add(new Question("What is the definite article for ‘Wetter’ (weather)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "das", "‘Wetter’ is neuter: ‘das Wetter’.", 1));
            qList.add(new Question("How do you say ‘It is windy’ in German?", QuestionType.MULTIPLE_CHOICE.name(), "Es ist windig", "Es macht Wind", "Es windet viel", "Es hat Wind", "A", "‘Es ist windig’ uses the adjective windig.", 1));
            qList.add(new Question("How do you say ‘It is snowing’ in German?", QuestionType.MULTIPLE_CHOICE.name(), "Es schneit", "Es schneit Schnee", "Es ist schneien", "Es macht Schnee", "A", "‘Es schneit’ is the impersonal verb for snowing.", 1));
            qList.add(new Question("Translate to German: ‘How is the weather today?’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Wie ist das Wetter heute? / Wie ist das Wetter heute", "‘Wie ist das Wetter heute?’ asks for the weather forecast.", 1));
            qList.add(new Question("Translate to English: ‘Es ist bewölkt und kalt.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "It is cloudy and cold", "‘bewölkt’ = cloudy, ‘kalt’ = cold.", 1));
            qList.add(new Question("What is the German word for ‘rain’ (noun)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Regen", "‘der Regen’ is rain.", 1));
            qList.add(new Question("What is the German word for ‘temperature’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "die Temperatur", "‘die Temperatur’ is temperature.", 1));
            qList.add(new Question("True or False: ‘warm’ and ‘heiß’ mean the exact same temperature level in German.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "‘warm’ is pleasant/warm (~22°C), while ‘heiß’ is hot (>30°C).", 1));
            qList.add(new Question("Complete the sentence: ‘Im Sommer ist es oft sehr ____.’ (hot)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "heiß", "‘heiß’ means hot.", 1));
            qList.add(new Question("What is the definite article for ‘Sonne’ (sun)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "die", "‘die Sonne’ is feminine.", 1));
            qList.add(new Question("Translate to English: ‘Es hat 25 Grad.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "It is 25 degrees / It's 25 degrees", "‘Grad’ means degrees Celsius.", 1));
            qList.add(new Question("Fill in the blank: Im Winter ____ es oft (schneien).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "schneit", "‘es schneit’ (it snows).", 1));
            saveQuestionsForTopic(mod4, t, qList, quizRepository, questionRepository);
        }

        // Topic 5: Feiertage & Feiern
        if (topics.size() > 4) {
            Topic t = topics.get(4);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("How do you wish someone ‘Merry Christmas’ in German?", QuestionType.MULTIPLE_CHOICE.name(), "Frohe Weihnachten!", "Frohe Ostern!", "Herzlichen Glückwunsch!", "Guten Rutsch!", "A", "‘Frohe Weihnachten!’ means Merry Christmas.", 1));
            qList.add(new Question("How do you wish someone ‘Happy Birthday’ in German?", QuestionType.MULTIPLE_CHOICE.name(), "Alles Gute zum Geburtstag!", "Frohe Weihnachten!", "Gute Besserung!", "Guten Tag!", "A", "‘Alles Gute zum Geburtstag!’ or ‘Herzlichen Glückwunsch zum Geburtstag!’.", 1));
            qList.add(new Question("What German greeting is used specifically before the New Year (Silvester)?", QuestionType.MULTIPLE_CHOICE.name(), "Guten Rutsch!", "Frohe Ostern!", "Gute Reise!", "Guten Tag!", "A", "‘Guten Rutsch!’ (literally 'a good slide into the new year') is used before midnight on New Year's Eve.", 1));
            qList.add(new Question("What is the German word for ‘Easter’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Ostern", "‘Ostern’ is Easter.", 1));
            qList.add(new Question("What is the German word for ‘New Year's Eve’ (December 31st)?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "Silvester", "‘Silvester’ is New Year's Eve.", 1));
            qList.add(new Question("Translate to German: ‘Happy New Year!’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Frohes neues Jahr! / Frohes neues Jahr / Ein frohes neues Jahr", "‘Frohes neues Jahr!’ means Happy New Year.", 1));
            qList.add(new Question("Translate to English: ‘das Geschenk’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "the present / the gift / present / gift", "‘das Geschenk’ is present/gift.", 1));
            qList.add(new Question("What is the German verb for ‘to celebrate’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "feiern", "‘feiern’ means to celebrate / party.", 1));
            qList.add(new Question("Conjugate ‘feiern’ for ‘wir’: Wir ____ seinen Geburtstag.", QuestionType.VERB_CONJUGATION.name(), null, null, null, null, "feiern", "‘wir feiern’ (we celebrate).", 1));
            qList.add(new Question("What is the definite article for ‘Geburtstag’ (birthday)? (der / die / das)", QuestionType.ARTICLE_PRACTICE.name(), null, null, null, null, "der", "‘der Geburtstag’ is masculine (der Tag).", 1));
            qList.add(new Question("Translate to English: ‘Herzlichen Glückwunsch!’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "Congratulations! / Warm congratulations! / Best wishes!", "‘Herzlichen Glückwunsch!’ means Congratulations!", 1));
            qList.add(new Question("Complete the sentence: ‘Ich lade dich zu meiner ____ ein.’ (birthday party / Party)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "Party", "‘die Party’ (zu meiner Party / Geburtstagsfeier).", 1));
            qList.add(new Question("What is the German word for ‘vacation / holiday trip’?", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "der Urlaub", "‘der Urlaub’ or ‘die Ferien’ means vacation.", 1));
            qList.add(new Question("True or False: ‘Silvester’ in German is celebrated on December 24th.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "B", "‘Silvester’ is December 31st. December 24th is ‘Heiligabend’ (Christmas Eve).", 1));
            qList.add(new Question("Translate to German: ‘Happy Easter!’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Frohe Ostern! / Frohe Ostern", "‘Frohe Ostern!’ means Happy Easter.", 1));
            saveQuestionsForTopic(mod4, t, qList, quizRepository, questionRepository);
        }

        // Topic 6: Satzstruktur & Konnektoren
        if (topics.size() > 5) {
            Topic t = topics.get(5);
            List<Question> qList = new ArrayList<>();
            qList.add(new Question("What position do coordinating conjunctions like ‘und’, ‘aber’, ‘oder’, ‘denn’ occupy in German syntax?", QuestionType.MULTIPLE_CHOICE.name(), "Position 0 (they do not count in word order)", "Position 1", "Position 2", "At the very end", "A", "Coordinating conjunctions (ADUSO: aber, denn, und, sondern, oder) sit at Position 0 and do not change word order.", 1));
            qList.add(new Question("Where does the conjugated verb move when a clause begins with the subordinating conjunction ‘weil’ (because)?", QuestionType.MULTIPLE_CHOICE.name(), "To the very end of the subordinate clause", "Position 2", "Position 1", "Before the subject", "A", "Subordinating conjunctions (weil, dass, wenn) kick the conjugated verb to the very end of the subordinate clause.", 1));
            qList.add(new Question("Fill in the connector: Ich bleibe zu Hause, ____ ich krank bin (because).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "weil", "‘weil’ introduces the reason with the verb 'bin' at the end.", 1));
            qList.add(new Question("Which connector introduces an opposing fact with Position 0 (meaning 'but')?", QuestionType.MULTIPLE_CHOICE.name(), "aber", "weil", "deshalb", "dass", "A", "‘aber’ means but (Position 0).", 1));
            qList.add(new Question("Where does the conjugated verb go after the adverbial connector ‘deshalb’ (therefore)?", QuestionType.MULTIPLE_CHOICE.name(), "Position 2 (immediately after deshalb)", "At the very end", "Position 3", "Position 1", "A", "‘deshalb’ takes Position 1, so the verb immediately follows at Position 2 (Inversion).", 1));
            qList.add(new Question("Translate to German: ‘I am tired because I worked a lot.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich bin müde, weil ich viel gearbeitet habe", "‘weil’ pushes the auxiliary 'habe' to the end.", 1));
            qList.add(new Question("Translate to English: ‘Er lernt Deutsch, denn er will in Deutschland arbeiten.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "He learns German because he wants to work in Germany", "‘denn’ means because (Position 0).", 1));
            qList.add(new Question("True or False: ‘dass’ (that) pushes the conjugated verb to the end of the clause.", QuestionType.TRUE_FALSE.name(), "True", "False", null, null, "A", "True! ‘Ich weiß, dass du Deutsch lernst’ (verb at end).", 1));
            qList.add(new Question("Fill in the connector: Er ist krank, ____ geht er zum Arzt (therefore).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "deshalb", "‘deshalb’ (Position 1) + verb ‘geht’ (Position 2).", 1));
            qList.add(new Question("Complete the sentence: ‘Ich trinke Kaffee ____ Tee.’ (or)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "oder", "‘oder’ means or.", 1));
            qList.add(new Question("Translate to German: ‘I think that you are right.’", QuestionType.ENGLISH_TO_GERMAN.name(), null, null, null, null, "Ich denke, dass du recht hast / Ich glaube, dass du recht hast", "‘dass’ sends 'hast' to the end.", 1));
            qList.add(new Question("What connector means ‘if / when’ for conditions?", QuestionType.MULTIPLE_CHOICE.name(), "wenn", "wann", "weil", "denn", "A", "‘wenn’ introduces conditional clauses (verb at end). ‘wann’ is only for questions.", 1));
            qList.add(new Question("Translate to English: ‘Ich habe Hunger, aber der Kühlschrank ist leer.’", QuestionType.GERMAN_TO_ENGLISH.name(), null, null, null, null, "I am hungry but the fridge is empty", "‘aber’ = but.", 1));
            qList.add(new Question("Fill in the connector: Ich lerne Deutsch, ____ ich finde die Sprache schön (and).", QuestionType.FILL_IN_THE_BLANK.name(), null, null, null, null, "und", "‘und’ means and.", 1));
            qList.add(new Question("Complete the sentence with verb at end: ‘Wenn es regnet, ____ ich zu Hause.’ (to stay / bleiben)", QuestionType.SENTENCE_COMPLETION.name(), null, null, null, null, "bleibe", "Inversion in the main clause following a subordinate clause: ‘bleibe ich zu Hause’.", 1));
            saveQuestionsForTopic(mod4, t, qList, quizRepository, questionRepository);
        }
    }
}

