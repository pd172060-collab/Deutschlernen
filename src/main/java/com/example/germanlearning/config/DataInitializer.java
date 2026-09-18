package com.example.germanlearning.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.germanlearning.entity.Lesson;
import com.example.germanlearning.entity.Module;
import com.example.germanlearning.entity.Question;
import com.example.germanlearning.entity.Quiz;
import com.example.germanlearning.entity.Slide;
import com.example.germanlearning.entity.Test;
import com.example.germanlearning.entity.Topic;
import com.example.germanlearning.entity.User;
import com.example.germanlearning.repository.LessonRepository;
import com.example.germanlearning.repository.ModuleRepository;
import com.example.germanlearning.repository.QuestionRepository;
import com.example.germanlearning.repository.QuizRepository;
import com.example.germanlearning.repository.TestRepository;
import com.example.germanlearning.repository.TopicRepository;
import com.example.germanlearning.repository.UserProgressRepository;
import com.example.germanlearning.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final UserProgressRepository userProgressRepository;
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final TestRepository testRepository;
    private final QuestionBankInitializer questionBankInitializer;

    public DataInitializer(
            ModuleRepository moduleRepository,
            TopicRepository topicRepository,
            LessonRepository lessonRepository,
            UserRepository userRepository,
            UserProgressRepository userProgressRepository,
            QuestionRepository questionRepository,
            QuizRepository quizRepository,
            TestRepository testRepository,
            QuestionBankInitializer questionBankInitializer) {
        this.moduleRepository = moduleRepository;
        this.topicRepository = topicRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.userProgressRepository = userProgressRepository;
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.testRepository = testRepository;
        this.questionBankInitializer = questionBankInitializer;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initUser();
        initModule1();
        initModule2();
        initModule3();
        initModule4();
        initQuestions();
        initQuizzes();
        initModuleTests();
    }

    private void initUser() {
        if (userRepository.count() == 0) {
            User demoUser = new User("learndeutsch", "user@deutschlernen.com", "German Learner");
            userRepository.save(demoUser);
        }
    }

    private void initModule1() {
        Optional<Module> existingOpt = moduleRepository.findByCode("MODULE_1");
        Module mod1;
        if (existingOpt.isPresent()) {
            mod1 = existingOpt.get();
            if (mod1.getTopics().size() == 7) {
                return;
            }
            userProgressRepository.deleteAll();
            mod1.getTopics().clear();
        } else {
            mod1 = new Module();
            mod1.setCode("MODULE_1");
        }

        mod1.setTitle("Module I: Foundations & Basic Communication");
        mod1.setGermanTitle("Modul I: Begrüßungen, Verben, W-Fragen, Alltag & Artikel");
        mod1.setDescription("Ein vollständiges Wortschatz- und Grammatikverzeichnis für dieses Modul, mit englischer Übersetzung für jedes Element. Master greetings, essential irregular verbs (haben & sein), W-questions, regular verb conjugations, articles, plural forms, calendar terms, and daily routines.");
        mod1.setLevel("A1");
        mod1.setOrderIndex(1);
        mod1.setActive(true);

        // TOPIC 1
        Topic topic1 = new Topic("Begrüßungen & Vorstellung (Greetings & Introductions)", "Begrüßungen & Vorstellung", "Kernvokabular und typische Sätze, um sich auf Deutsch zu begrüßen und vorzustellen.", 1, mod1);
        Lesson lesson1_1 = new Lesson("Kernvokabular: Begrüßungen & Abschiede", "Begrüßungen & Abschiede", "Lerne die wichtigsten Begrüßungen und Abschiedsformeln auf Deutsch.", 1, 8, topic1);
        Slide s1_1_1 = new Slide("Alltägliche Begrüßungen (Everyday Greetings)", 1, "VOCABULARY", lesson1_1);
        s1_1_1.setGermanText("Hallo / Guten Tag\nGuten Morgen\nGuten Abend");
        s1_1_1.setEnglishTranslation("Hello / Good day\nGood morning\nGood evening");
        s1_1_1.setExplanation("In German, greetings depend on the time of day and degree of formality. 'Guten Morgen' is typically used until ~11 AM, 'Guten Tag' throughout the day, and 'Guten Abend' from ~6 PM.");
        s1_1_1.setVocabulary("Hallo (Hello) | Guten Tag (Good day) | Guten Morgen (Good morning) | Guten Abend (Good evening)");
        s1_1_1.setGrammarRule("All German nouns (Tag, Morgen, Abend) are always capitalized.");
        s1_1_1.setExamples("Guten Morgen, Herr Müller! (Good morning, Mr. Müller!)\nGuten Tag! Wie geht es Ihnen? (Good day! How are you?)\nGuten Abend allerseits! (Good evening everyone!)");
        s1_1_1.setImportantNote("'Hallo' is friendly and informal. 'Guten Tag' is the polite standard in stores and business meetings.");
        lesson1_1.addSlide(s1_1_1);

        Slide s1_1_2 = new Slide("Verabschiedung: Formell vs. Informell (Goodbyes)", 2, "VOCABULARY", lesson1_1);
        s1_1_2.setGermanText("Auf Wiedersehen (formell)\nTschüss (informell)");
        s1_1_2.setEnglishTranslation("Goodbye (formal)\nBye (informal)");
        s1_1_2.setExplanation("'Auf Wiedersehen' literally translates to 'until we see each other again' and is the standard formal goodbye. 'Tschüss' is the most common informal farewell among friends, family, and colleagues.");
        s1_1_2.setVocabulary("Auf Wiedersehen (Goodbye - formal) | Tschüss (Bye - informal)");
        s1_1_2.setGrammarRule("When speaking on the telephone in a formal context, use 'Auf Wiederhören' (until we hear each other again).");
        s1_1_2.setExamples("Auf Wiedersehen, Frau Schmidt! (Goodbye, Ms. Schmidt!)\nTschüss, bis morgen! (Bye, see you tomorrow!)");
        s1_1_2.setImportantNote("Always use 'Auf Wiedersehen' with strangers, in shops, and in formal business environments.");
        lesson1_1.addSlide(s1_1_2);
        topic1.addLesson(lesson1_1);

        Lesson lesson1_2 = new Lesson("Wichtige Verben zur Vorstellung", "Wichtige Verben zur Vorstellung", "Wichtige Verben und Nomen zur eigenen Vorstellung und Herkunft.", 2, 10, topic1);
        Slide s1_2_1 = new Slide("Grundverben zur Person (Core Personal Verbs & Nouns)", 1, "VOCABULARY", lesson1_2);
        s1_2_1.setGermanText("der Name — the name\nheißen — to be called / named\nkommen aus — to come from\nwohnen in — to live in\nsprechen — to speak");
        s1_2_1.setEnglishTranslation("the name\nto be called / named\nto come from\nto live in\nto speak");
        s1_2_1.setExplanation("These four essential verbs (heißen, kommen, wohnen, sprechen) combined with 'der Name' allow you to provide complete personal introductory details.");
        s1_2_1.setVocabulary("der Name (the name) | heißen (to be called) | kommen aus (to come from) | wohnen in (to live in) | sprechen (to speak)");
        s1_2_1.setGrammarRule("Preposition patterns:\n• kommen + aus (origin: country or city)\n• wohnen + in (location: city or street)");
        s1_2_1.setExamples("Mein Name ist Thomas. (My name is Thomas.)\nIch komme aus Deutschland. (I come from Germany.)\nIch wohne in Berlin. (I live in Berlin.)\nIch spreche Deutsch. (I speak German.)");
        s1_2_1.setImportantNote("'der Name' has masculine gender (der). Pay close attention to the prepositions 'aus' and 'in'.");
        lesson1_2.addSlide(s1_2_1);
        topic1.addLesson(lesson1_2);

        Lesson lesson1_3 = new Lesson("Fragen & Antworten: Sich Vorstellen", "Fragen & Antworten zur Vorstellung", "Dialoge und Redemittel, um nach Namen, Herkunft und Wohnort zu fragen.", 3, 10, topic1);
        Slide s1_3_1 = new Slide("Nach dem Namen fragen (Asking & Stating Names)", 1, "DIALOGUE", lesson1_3);
        s1_3_1.setGermanText("Wie heißen Sie? / Wie heißt du?\nIch heiße Thomas. / Mein Name ist Thomas.");
        s1_3_1.setEnglishTranslation("What is your name? (formal / informal)\nMy name is Thomas. / I am called Thomas.");
        s1_3_1.setExplanation("In German, asking someone's name uses the question word 'Wie' (How). Use 'Wie heißen Sie?' for formal situations and 'Wie heißt du?' with children, friends, or peers.");
        s1_3_1.setVocabulary("wie (what/how) | heißen (to be called) | Sie (you - formal) | du (you - informal) | mein Name (my name)");
        s1_3_1.setGrammarRule("In German, asking for a name is constructed with 'Wie' (how), not 'Was' (what): 'Wie heißen Sie?'.");
        s1_3_1.setExamples("A: Wie heißen Sie?\nB: Ich heiße Thomas.\nA: Wie heißt du?\nB: Mein Name ist Thomas.");
        s1_3_1.setImportantNote("Both 'Ich heiße [Name]' and 'Mein Name ist [Name]' are interchangeable and natural.");
        lesson1_3.addSlide(s1_3_1);

        Slide s1_3_2 = new Slide("Herkunft, Wohnort & Begrüßung (Origin & Residence)", 2, "DIALOGUE", lesson1_3);
        s1_3_2.setGermanText("Woher kommen Sie? / Woher kommst du?\nIch komme aus Deutschland.\n\nWo wohnen Sie? / Wo wohnst du?\nIch wohne in Berlin.\n\nFreut mich, Sie kennenzulernen.");
        s1_3_2.setEnglishTranslation("Where do you come from? (formal / informal)\nI come from Germany.\n\nWhere do you live? (formal / informal)\nI live in Berlin.\n\nNice to meet you.");
        s1_3_2.setExplanation("'Woher' asks for place of origin (paired with 'aus'), while 'Wo' asks for current residence (paired with 'in'). 'Freut mich, Sie kennenzulernen' is standard polite etiquette when meeting someone.");
        s1_3_2.setVocabulary("woher (where from) | Deutschland (Germany) | wo (where) | wohnen (to live) | Berlin (Berlin) | freut mich (nice to meet you) | kennenlernen (to get to know)");
        s1_3_2.setGrammarRule("Preposition distinction:\n• Woher kommst du? -> Ich komme aus [Land/Stadt].\n• Wo wohnst du? -> Ich wohne in [Stadt].");
        s1_3_2.setExamples("A: Woher kommen Sie, Herr Thomas?\nB: Ich komme aus Deutschland.\nA: Wo wohnen Sie jetzt?\nB: Ich wohne in Berlin. Freut mich, Sie kennenzulernen!");
        s1_3_2.setImportantNote("'Freut mich, Sie kennenzulernen' is formal. Informally with friends, you can simply say 'Freut mich!'.");
        lesson1_3.addSlide(s1_3_2);
        topic1.addLesson(lesson1_3);
        mod1.addTopic(topic1);

        // TOPIC 2
        Topic topic2 = new Topic("Die Verben \"haben\" und \"sein\" (The Verbs \"to have\" and \"to be\")", "Die Verben \"haben\" und \"sein\"", "Diese beiden unregelmäßigen Verben sind die wichtigsten Grundlagen der deutschen Sprache.", 2, mod1);
        Lesson lesson2_1 = new Lesson("Das Verb \"sein\" (The Verb \"to be\")", "Das Verb \"sein\"", "Konjugation und praktische Beispielsätze für das Hilfsverb sein.", 1, 10, topic2);
        Slide s2_1_1 = new Slide("Konjugation: sein (to be)", 1, "TABLE", lesson2_1);
        s2_1_1.setGermanText("ich bin\ndu bist\ner/sie/es ist\nwir sind\nihr seid\nsie / Sie sind");
        s2_1_1.setEnglishTranslation("I am\nyou are (sg. informal)\nhe/she/it is\nwe are\nyou all are (pl. informal)\nthey / you are (formal)");
        s2_1_1.setExplanation("The verb 'sein' (to be) is irregular in almost every form. It is the most frequent verb in the German language.");
        s2_1_1.setVocabulary("ich (I) | du (you sg.) | er (he) | sie (she) | es (it) | wir (we) | ihr (you all) | sie (they) | Sie (you formal)");
        s2_1_1.setGrammarRule("Conjugation Table for 'sein':\n• ich bin (I am)\n• du bist (you are)\n• er/sie/es ist (he/she/it is)\n• wir sind (we are)\n• ihr seid (you all are)\n• sie/Sie sind (they/you formal are)");
        s2_1_1.setExamples("ich bin — I am\ndu bist — you are\ner/sie/es ist — he/she/it is\nwir sind — we are\nihr seid — you are (plural)\nsie/Sie sind — they/you (formal) are");
        s2_1_1.setImportantNote("Be careful with spelling: 'ihr seid' ends with 'd'. ('seit' with 't' is a preposition meaning 'since').");
        lesson2_1.addSlide(s2_1_1);

        Slide s2_1_2 = new Slide("Beispielsätze mit \"sein\" (Examples with \"sein\")", 2, "EXAMPLE", lesson2_1);
        s2_1_2.setGermanText("Ich bin glücklich.\nDu bist mein Freund.\nEr ist müde.\nWir sind zu Hause.\nIhr seid sehr nett.\nSie sind aus Spanien.");
        s2_1_2.setEnglishTranslation("I am happy.\nYou are my friend.\nHe is tired.\nWe are at home.\nYou all are very nice.\nThey/You are from Spain.");
        s2_1_2.setExplanation("'sein' is used to state identity, feelings, physical states, origins, and locations.");
        s2_1_2.setVocabulary("glücklich (happy) | der Freund (friend) | müde (tired) | zu Hause (at home) | sehr nett (very nice) | Spanien (Spain)");
        s2_1_2.setGrammarRule("Predicate adjectives following 'sein' (e.g. glücklich, müde, nett) never take adjective endings in German.");
        s2_1_2.setExamples("Ich bin glücklich. (I am happy.)\nEr ist müde. (He is tired.)\nWir sind zu Hause. (We are at home.)");
        s2_1_2.setImportantNote("'zu Hause' is a fixed German idiom meaning 'at home' (location), while 'nach Hause' means 'towards home' (direction).");
        lesson2_1.addSlide(s2_1_2);
        topic2.addLesson(lesson2_1);

        Lesson lesson2_2 = new Lesson("Das Verb \"haben\" (The Verb \"to have\")", "Das Verb \"haben\"", "Konjugation und praktische Beispielsätze für das Hilfsverb haben.", 2, 10, topic2);
        Slide s2_2_1 = new Slide("Konjugation: haben (to have)", 1, "TABLE", lesson2_2);
        s2_2_1.setGermanText("ich habe\ndu hast\ner/sie/es hat\nwir haben\nihr habt\nsie / Sie haben");
        s2_2_1.setEnglishTranslation("I have\nyou have (sg. informal)\nhe/she/it has\nwe have\nyou all have (pl. informal)\nthey / you have (formal)");
        s2_2_1.setExplanation("'haben' (to have) is an irregular verb. Notice that the stem drops the letter 'b' in the 2nd and 3rd person singular forms: 'du hast' and 'er/sie/es hat'.");
        s2_2_1.setVocabulary("haben (to have) | das Auto (car) | die Zeit (time) | der Hund (dog) | der Hunger (hunger) | die Frage (question) | die Arbeit (work)");
        s2_2_1.setGrammarRule("Conjugation Table for 'haben':\n• ich habe\n• du hast (stem drops -b-)\n• er/sie/es hat (stem drops -b-)\n• wir haben\n• ihr habt\n• sie/Sie haben");
        s2_2_1.setExamples("ich habe — I have\ndu hast — you have\ner/sie/es hat — he/she/it has\nwir haben — we have\nihr habt — you all have\nsie/Sie haben — they/you (formal) have");
        s2_2_1.setImportantNote("Always remember the stem change: 'du hast' (not 'habst') and 'er hat' (not 'habt').");
        lesson2_2.addSlide(s2_2_1);

        Slide s2_2_2 = new Slide("Beispielsätze mit \"haben\" (Examples with \"haben\")", 2, "EXAMPLE", lesson2_2);
        s2_2_2.setGermanText("Ich habe ein Auto.\nDu hast Zeit.\nSie hat einen Hund.\nWir haben Hunger.\nHabt ihr Fragen?\nSie haben viel Arbeit.");
        s2_2_2.setEnglishTranslation("I have a car.\nYou have time.\nShe has a dog.\nWe are hungry.\nDo you all have questions?\nThey/You have a lot of work.");
        s2_2_2.setExplanation("'haben' expresses possession, needs, and physical states (e.g. 'Hunger haben' = to be hungry).");
        s2_2_2.setVocabulary("das Auto (car) | die Zeit (time) | der Hund (dog) | der Hunger (hunger) | die Frage (question) | die Arbeit (work) | viel (a lot of)");
        s2_2_2.setGrammarRule("Objects following 'haben' are in the Accusative case:\n• Masculine: einen Hund (der Hund -> einen Hund)\n• Neuter: ein Auto (das Auto -> ein Auto)\n• Feminine: eine Frage (die Frage -> eine Frage)");
        s2_2_2.setExamples("Ich habe ein Auto. (I have a car.)\nWir haben Hunger. (We are hungry.)\nSie haben viel Arbeit. (They/You have a lot of work.)");
        s2_2_2.setImportantNote("German expresses physical needs with 'haben': 'Ich habe Hunger' (literally 'I have hunger') = 'I am hungry'.");
        lesson2_2.addSlide(s2_2_2);
        topic2.addLesson(lesson2_2);
        mod1.addTopic(topic2);

        // TOPIC 3
        Topic topic3 = new Topic("Personalpronomen & W-Fragen (Personal Pronouns & W-Questions)", "Personalpronomen & W-Fragen", "Fragewörter, um detaillierte Informationen in einer Unterhaltung zu erhalten.", 3, mod1);
        Lesson lesson3_1 = new Lesson("W-Fragen Übersicht (Overview of W-Questions)", "Die W-Fragewörter", "Übersicht und Bedeutung der 7 wichtigsten W-Fragewörter im Deutschen.", 1, 10, topic3);
        Slide s3_1_1 = new Slide("Die 7 W-Fragewörter (The 7 W-Words)", 1, "TABLE", lesson3_1);
        s3_1_1.setGermanText("Wer? — Who?\nWas? — What?\nWo? — Where?\nWoher? — Where from?\nWann? — When?\nWie? — How?\nWarum? — Why?");
        s3_1_1.setEnglishTranslation("Wer? — Who?\nWas? — What?\nWo? — Where?\nWoher? — Where from?\nWann? — When?\nWie? — How?\nWarum? — Why?");
        s3_1_1.setExplanation("W-Fragen (open questions) always begin with a question word starting with 'W'. Each word targets a distinct piece of information.");
        s3_1_1.setVocabulary("Wer? (Who?) | Was? (What?) | Wo? (Where?) | Woher? (Where from?) | Wann? (When?) | Wie? (How?) | Warum? (Why?)");
        s3_1_1.setGrammarRule("W-Words Summary:\n• Wer? -> Asks about a person (Who)\n• Was? -> Asks about a thing or action (What)\n• Wo? -> Asks about a static location (Where)\n• Woher? -> Asks about origin or source (Where from)\n• Wann? -> Asks about time (When)\n• Wie? -> Asks about manner, state, or name (How)\n• Warum? -> Asks about reasons or causes (Why)");
        s3_1_1.setExamples("Wer? -> Wer ist das?\nWas? -> Was machst du?\nWo? -> Wo wohnst du?");
        s3_1_1.setImportantNote("Beware of the false friend: German 'Wer' means 'Who', while German 'Wo' means 'Where'!");
        lesson3_1.addSlide(s3_1_1);

        Slide s3_1_2 = new Slide("Beispielsätze mit W-Fragen (W-Question Examples)", 2, "EXAMPLE", lesson3_1);
        s3_1_2.setGermanText("Wer ist das? (Who is that?)\nWas machst du? (What are you doing?)\nWo wohnst du? (Where do you live?)\nWoher kommst du? (Where do you come from?)\nWann hast du Zeit? (When do you have time?)\nWie alt bist du? (How old are you?)\nWarum lernst du Deutsch? (Why do you learn German?)");
        s3_1_2.setEnglishTranslation("Who is that?\nWhat are you doing?\nWhere do you live?\nWhere do you come from?\nWhen do you have time?\nHow old are you?\nWhy do you learn German?");
        s3_1_2.setExplanation("These sample questions demonstrate everyday usage of each W-word when asking for details in German conversations.");
        s3_1_2.setVocabulary("alt (old) | die Zeit (time) | lernen (to learn) | Deutsch (German)");
        s3_1_2.setGrammarRule("In each question, the conjugated verb immediately follows the question word (Position 2), and the subject pronoun follows the verb (Position 3).");
        s3_1_2.setExamples("Wann hast du Zeit? (When do you have time?)\nWie alt bist du? (How old are you?)\nWarum lernst du Deutsch? (Why do you learn German?)");
        s3_1_2.setImportantNote("'Wie alt bist du?' (How old are you?) uses 'sein' (bist) just like in English.");
        lesson3_1.addSlide(s3_1_2);
        topic3.addLesson(lesson3_1);

        Lesson lesson3_2 = new Lesson("Satzbau bei W-Fragen (Sentence Structure in W-Questions)", "Satzbau bei W-Fragen", "Die grundlegende Satzbauregel für deutsche W-Fragen: Position 1 und Position 2.", 2, 8, topic3);
        Slide s3_2_1 = new Slide("Regel: Position 1 & Position 2 (Word Order Rule)", 1, "GRAMMAR", lesson3_2);
        s3_2_1.setGermanText("Regel (Satzbau bei W-Fragen):\nBei einer W-Frage steht das W-Wort immer auf Position 1 und das konjugierte Verb auf Position 2.\n\nBeispiel:\n\"Wo (1) wohnst (2) du (3)?\"");
        s3_2_1.setEnglishTranslation("Rule (Sentence Structure in W-Questions):\nIn a W-question, the W-word is always on Position 1 and the conjugated verb on Position 2.\n\nExample:\n\"Wo (1) wohnst (2) du (3)?\" (Where do you live?)");
        s3_2_1.setExplanation("Word order in German questions is strictly governed:\n1. Position 1: W-Word (Fragewort)\n2. Position 2: Conjugated Verb (Konjugiertes Verb)\n3. Position 3: Subject (Subjekt)\n4. Remaining elements (Object, time, place)");
        s3_2_1.setVocabulary("die Regel (rule) | der Satzbau (sentence structure) | das W-Wort (W-word) | das Verb (verb) | das Subjekt (subject)");
        s3_2_1.setGrammarRule("Formula for W-Questions:\n[Position 1: W-Wort] + [Position 2: Verb] + [Position 3: Subjekt] + [Rest]?\n• Wo (1) wohnst (2) du (3)?\n• Was (1) machst (2) du (3)?\n• Warum (1) lernst (2) du (3) Deutsch?");
        s3_2_1.setExamples("Wo (1) wohnst (2) du? (Where do you live?)\nWoher (1) kommst (2) du? (Where do you come from?)\nWann (1) hast (2) du (3) Zeit? (When do you have time?)");
        s3_2_1.setImportantNote("Never put the subject before the verb in a W-question (e.g. *Wo du wohnst? is incorrect!). The verb must always be in Position 2.");
        lesson3_2.addSlide(s3_2_1);
        topic3.addLesson(lesson3_2);
        mod1.addTopic(topic3);

        // TOPIC 4
        Topic topic4 = new Topic("Regelmäßige Verben & Einfacher Satzbau (Regular Verbs & Sentence Structure)", "Regelmäßige Verben & Einfacher Satzbau", "Endungen für regelmäßige Verben und die Satzbauregel für einfache Aussagesätze.", 4, mod1);
        Lesson lesson4_1 = new Lesson("Endungen für regelmäßige Verben (Regular Verb Endings)", "Konjugation regelmäßiger Verben", "Lerne die Personalendungen für regelmäßige Verben im Präsens.", 1, 10, topic4);
        Slide s4_1_1 = new Slide("Endungen für regelmäßige Verben (Verb Endings)", 1, "TABLE", lesson4_1);
        s4_1_1.setGermanText("Pronomen | Endung (Ending)\nich | -e\ndu | -st\ner/sie/es | -t\nwir | -en\nihr | -t\nsie/Sie | -en");
        s4_1_1.setEnglishTranslation("Pronoun | Ending\nich (I) | -e\ndu (you sg.) | -st\ner/sie/es (he/she/it) | -t\nwir (we) | -en\nihr (you all) | -t\nsie/Sie (they/you formal) | -en");
        s4_1_1.setExplanation("To conjugate any regular German verb: take the infinitive (e.g., lernen), drop the '-en' to get the root stem (lern-), and add the specific ending for each person.");
        s4_1_1.setVocabulary("das Pronomen (pronoun) | die Endung (ending) | der Verbstamm (verb stem) | regelmäßig (regular)");
        s4_1_1.setGrammarRule("Regular Verb Endings:\n• ich -> -e\n• du -> -st\n• er/sie/es -> -t\n• wir -> -en\n• ihr -> -t\n• sie/Sie -> -en");
        s4_1_1.setExamples("lernen -> Stamm: lern-\nmachen -> Stamm: mach-");
        s4_1_1.setImportantNote("Notice that 'wir' and 'sie/Sie' always take the ending '-en' and look identical to the infinitive.");
        lesson4_1.addSlide(s4_1_1);

        Slide s4_1_2 = new Slide("Beispiele: \"lernen\" & \"machen\" (Conjugation Examples)", 2, "TABLE", lesson4_1);
        s4_1_2.setGermanText("lernen (to learn):\n• ich lerne\n• du lernst\n• er lernt\n• wir lernen\n• ihr lernt\n• sie lernen\n\nmachen (to do / make):\n• ich mache\n• du machst\n• sie macht\n• wir machen\n• ihr macht\n• Sie machen");
        s4_1_2.setEnglishTranslation("lernen (to learn):\n• I learn\n• you learn\n• he learns\n• we learn\n• you all learn\n• they learn\n\nmachen (to do / make):\n• I do / make\n• you do / make\n• she does / makes\n• we do / make\n• you all do / make\n• you (formal) do / make");
        s4_1_2.setExplanation("Here is the full conjugation of 'lernen' (to learn) and 'machen' (to do/make) side-by-side using the regular ending pattern.");
        s4_1_2.setVocabulary("lernen (to learn) | machen (to do / make) | er lernt (he learns) | sie macht (she does/makes)");
        s4_1_2.setGrammarRule("Pattern Application:\n• ich lern-e / mach-e\n• du lern-st / mach-st\n• er/sie/es lern-t / mach-t\n• wir lern-en / mach-en\n• ihr lern-t / mach-t\n• sie/Sie lern-en / mach-en");
        s4_1_2.setExamples("Ich lerne Deutsch. (I learn German.)\nSie macht Hausaufgaben. (She does homework.)\nWir lernen zusammen. (We learn together.)");
        s4_1_2.setImportantNote("'machen' can mean both 'to do' (Was machst du?) and 'to make' (Ich mache Kaffee).");
        lesson4_1.addSlide(s4_1_2);
        topic4.addLesson(lesson4_1);

        Lesson lesson4_2 = new Lesson("Einfacher Satzbau (Simple Sentence Structure)", "Der einfache Aussagesatz", "Die grundlegende Satzbauregel für deutsche Hauptsätze: Subjekt (1) + Verb (2) + Objekt (3).", 2, 8, topic4);
        Slide s4_2_1 = new Slide("Satzbauregel für Aussagesätze (S-V-O Rule)", 1, "GRAMMAR", lesson4_2);
        s4_2_1.setGermanText("Satzbau / Sentence Structure:\nDer einfache Aussagesatz folgt der Regel:\nSubjekt (1) + Verb (2) + Objekt (3).\n\nBeispiel:\n\"Ich (Subject) lerne (Verb) Deutsch (Object).\"\n\nDas Verb steht in einem normalen Satz immer auf Position 2!");
        s4_2_1.setEnglishTranslation("Sentence Structure:\nThe simple declarative sentence follows the rule:\nSubject (1) + Verb (2) + Object (3).\n\nExample:\n\"Ich (Subject) lerne (Verb) Deutsch (Object).\" (I learn German.)\n\nThe verb is always on Position 2 in a normal sentence!");
        s4_2_1.setExplanation("In German declarative sentences, the conjugated verb is anchored strictly to Position 2. The standard word order is Subject -> Verb -> Object.");
        s4_2_1.setVocabulary("das Subjekt (subject) | das Verb (verb) | das Objekt (object) | der Aussagesatz (declarative sentence)");
        s4_2_1.setGrammarRule("Golden Rule of German Sentence Structure:\nPosition 1: Subjekt (Ich)\nPosition 2: Konjugiertes Verb (lerne)\nPosition 3: Objekt / Ergänzung (Deutsch)");
        s4_2_1.setExamples("Ich (1) lerne (2) Deutsch (3). (I learn German.)\nEr (1) trinkt (2) Wasser (3). (He drinks water.)\nWir (1) wohnen (2) in Berlin (3). (We live in Berlin.)");
        s4_2_1.setImportantNote("Always keep the conjugated verb in Position 2 in standard statement sentences!");
        lesson4_2.addSlide(s4_2_1);
        topic4.addLesson(lesson4_2);
        mod1.addTopic(topic4);

        // TOPIC 5
        Topic topic5 = new Topic("Artikel & Plural (Articles & Plural Forms)", "Artikel & Pluralformen", "Bestimmte und unbestimmte Artikel nach Genus sowie die wichtigsten Pluralformen der Nomen.", 5, mod1);
        Lesson lesson5_1 = new Lesson("Bestimmte & Unbestimmte Artikel (Definite & Indefinite Articles)", "Bestimmte & Unbestimmte Artikel", "Maskulin (der/ein), Feminin (die/eine), Neutral (das/ein) und Plural (die/-).", 1, 10, topic5);
        Slide s5_1_1 = new Slide("Artikel nach Genus (Articles by Gender)", 1, "TABLE", lesson5_1);
        s5_1_1.setGermanText("Genus (Gender) | Bestimmter Artikel (the) | Unbestimmter Artikel (a/an) | Beispiel / Example\nMaskulin (m) | der | ein | der Mann / ein Mann\nFeminin (f) | die | eine | die Frau / eine Frau\nNeutral (n) | das | ein | das Kind / ein Kind\nPlural (pl) | die | (kein unbestimmter Artikel) | die Kinder / Kinder");
        s5_1_1.setEnglishTranslation("Gender | Definite Article (the) | Indefinite Article (a/an) | Example\nMasculine (m) | der (the) | ein (a) | the man / a man\nFeminine (f) | die (the) | eine (a) | the woman / a woman\nNeuter (n) | das (the) | ein (a) | the child / a child\nPlural (pl) | die (the) | (no indefinite article) | the children / children");
        s5_1_1.setExplanation("Every German noun possesses an inherent grammatical gender (masculine, feminine, or neuter). Definite articles correspond to English 'the', while indefinite articles correspond to 'a/an'. Plural nouns have no indefinite article.");
        s5_1_1.setVocabulary("der Mann (the man) | die Frau (the woman) | das Kind (the child) | die Kinder (the children)");
        s5_1_1.setGrammarRule("Article Overview:\n• Maskulin: der (the) / ein (a)\n• Feminin: die (the) / eine (a)\n• Neutral: das (the) / ein (a)\n• Plural: die (the) / — (no article)");
        s5_1_1.setExamples("der Mann / ein Mann (the man / a man)\ndie Frau / eine Frau (the woman / a woman)\ndas Kind / ein Kind (the child / a child)\ndie Kinder / Kinder (the children / children)");
        s5_1_1.setImportantNote("Always learn each new German noun with its definite article (der, die, das) from day one!");
        lesson5_1.addSlide(s5_1_1);
        topic5.addLesson(lesson5_1);

        Lesson lesson5_2 = new Lesson("Pluralformen der Nomen (Plural Forms of Nouns)", "Pluralformen der Nomen", "Häufige Pluralmuster im Deutschen anhand konkreter Vokabeln aus dem Modul.", 2, 10, topic5);
        Slide s5_2_1 = new Slide("Häufige Pluralformen (Common Plural Forms)", 1, "TABLE", lesson5_2);
        s5_2_1.setGermanText("Singular | Plural | English\ndas Buch | die Bücher | the books\nder Tisch | die Tische | the tables\ndie Tasche | die Taschen | the bags\ndas Auto | die Autos | the cars\nder Lehrer | die Lehrer (keine Änderung) | the teachers");
        s5_2_1.setEnglishTranslation("das Buch -> die Bücher (the books)\nder Tisch -> die Tische (the tables)\ndie Tasche -> die Taschen (the bags)\ndas Auto -> die Autos (the cars)\nder Lehrer -> die Lehrer (no change) (the teachers)");
        s5_2_1.setExplanation("In German, plural endings vary based on the noun:\n1. -er with Umlaut: das Buch -> die Bücher\n2. -e: der Tisch -> die Tische\n3. -n / -en: die Tasche -> die Taschen\n4. -s (loanwords/modern): das Auto -> die Autos\n5. No ending change: der Lehrer -> die Lehrer");
        s5_2_1.setVocabulary("das Buch / die Bücher (book/books) | der Tisch / die Tische (table/tables) | die Tasche / die Taschen (bag/bags) | das Auto / die Autos (car/cars) | der Lehrer / die Lehrer (teacher/teachers)");
        s5_2_1.setGrammarRule("All German plural nouns share the exact same definite article: 'die' (die Bücher, die Tische, die Taschen, die Autos, die Lehrer).");
        s5_2_1.setExamples("Das Buch liegt auf dem Tisch. -> Die Bücher liegen auf den Tischen.\nDie Tasche ist neu. -> Die Taschen sind neu.\nDer Lehrer spricht. -> Die Lehrer sprechen.");
        s5_2_1.setImportantNote("Masculine nouns ending in '-er' (like der Lehrer) typically undergo no spelling change in the plural (der Lehrer -> die Lehrer).");
        lesson5_2.addSlide(s5_2_1);
        topic5.addLesson(lesson5_2);
        mod1.addTopic(topic5);

        // TOPIC 6
        Topic topic6 = new Topic("Wochentage, Monate & Jahreszeiten (Days, Months & Seasons)", "Wochentage, Monate & Jahreszeiten", "Wochentage mit der Präposition \"am\" sowie Monate und Jahreszeiten mit der Präposition \"im\".", 6, mod1);
        Lesson lesson6_1 = new Lesson("Die Wochentage (Days of the Week)", "Die Wochentage", "Die 7 Wochentage, das Wochenende und die Zeitpräposition \"am\".", 1, 8, topic6);
        Slide s6_1_1 = new Slide("Wochentage mit \"am\" (Days of the Week with \"am\")", 1, "TABLE", lesson6_1);
        s6_1_1.setGermanText("Wochentage (Days) — \"am\":\nder Montag — Monday\nder Dienstag — Tuesday\nder Mittwoch — Wednesday\nder Donnerstag — Thursday\nder Freitag — Friday\nder Samstag — Saturday\nder Sonntag — Sunday\ndas Wochenende — the weekend");
        s6_1_1.setEnglishTranslation("der Montag — Monday\nder Dienstag — Tuesday\nder Mittwoch — Wednesday\nder Donnerstag — Thursday\nder Freitag — Friday\nder Samstag — Saturday\nder Sonntag — Sunday\ndas Wochenende — the weekend");
        s6_1_1.setExplanation("All days of the week are grammatically masculine (der Montag, der Dienstag, etc.). When describing an event happening on a specific day or on the weekend, use the preposition 'am' (an + dem).");
        s6_1_1.setVocabulary("der Montag (Monday) | der Dienstag (Tuesday) | der Mittwoch (Wednesday) | der Donnerstag (Thursday) | der Freitag (Friday) | der Samstag (Saturday) | der Sonntag (Sunday) | das Wochenende (the weekend)");
        s6_1_1.setGrammarRule("Time Preposition 'am':\nUse 'am' with all days of the week:\n• am Montag (on Monday)\n• am Dienstag (on Tuesday)\n• am Freitag (on Friday)\n• am Wochenende (on the weekend)");
        s6_1_1.setExamples("Am Montag arbeite ich. (On Monday I work.)\nAm Samstag treffe ich Freunde. (On Saturday I meet friends.)\nWas machst du am Wochenende? (What are you doing on the weekend?)");
        s6_1_1.setImportantNote("Always remember to use 'am' (never 'in' or 'auf') with days of the week!");
        lesson6_1.addSlide(s6_1_1);
        topic6.addLesson(lesson6_1);

        Lesson lesson6_2 = new Lesson("Monate & Jahreszeiten (Months & Seasons)", "Monate & Jahreszeiten", "Die 4 Jahreszeiten, die 12 Monate und die Zeitpräposition \"im\".", 2, 10, topic6);
        Slide s6_2_1 = new Slide("Monate & Jahreszeiten mit \"im\" (Months & Seasons)", 1, "TABLE", lesson6_2);
        s6_2_1.setGermanText("Monate & Jahreszeiten (Months & Seasons) — \"im\":\n\nder Frühling (Spring) -> März, April, Mai (March, April, May)\nder Sommer (Summer) -> Juni, Juli, August (June, July, August)\nder Herbst (Autumn) -> September, Oktober, November (Sept., Oct., Nov.)\nder Winter (Winter) -> Dezember, Januar, Februar (Dec., Jan., Feb.)");
        s6_2_1.setEnglishTranslation("Seasons & Months:\n\nder Frühling (Spring) -> March, April, May\nder Sommer (Summer) -> June, July, August\nder Herbst (Autumn) -> September, October, November\nder Winter (Winter) -> December, January, February");
        s6_2_1.setExplanation("All seasons and all 12 calendar months are masculine in German (der Frühling, der Januar, der Juli). When saying 'in spring' or 'in August', use the preposition 'im' (in + dem).");
        s6_2_1.setVocabulary("der Frühling (Spring) | der Sommer (Summer) | der Herbst (Autumn) | der Winter (Winter) | März | April | Mai | Juni | Juli | August | September | Oktober | November | Dezember | Januar | Februar");
        s6_2_1.setGrammarRule("Preposition Rule:\n• Days of the week -> \"am\" (am Montag)\n• Months & Seasons -> \"im\" (im Sommer, im Juli)");
        s6_2_1.setExamples("Im Frühling blühen die Blumen. (In spring flowers bloom.)\nIm Sommer reisen wir nach Spanien. (In summer we travel to Spain.)\nMein Geburtstag ist im Oktober. (My birthday is in October.)\nIm Winter ist es kalt. (In winter it is cold.)");
        s6_2_1.setImportantNote("Rule of thumb: 'am' for Days, 'im' for Months and Seasons!");
        lesson6_2.addSlide(s6_2_1);
        topic6.addLesson(lesson6_2);
        mod1.addTopic(topic6);

        // TOPIC 7
        Topic topic7 = new Topic("Hobbys & Alltag (Hobbies & Daily Routines)", "Hobbys & Alltag", "Wortschatz zu Hobbys und Freizeit sowie typische Fragen und Antworten zum Tagesablauf.", 7, mod1);
        Lesson lesson7_1 = new Lesson("Wortschatz: Hobbys & Routinen", "Wortschatz: Hobbys & Routinen", "Wichtige Tätigkeiten, Hobbys und Alltagsaktivitäten im Deutschen.", 1, 10, topic7);
        Slide s7_1_1 = new Slide("Aktivitäten & Verben (Activities & Action Verbs)", 1, "VOCABULARY", lesson7_1);
        s7_1_1.setGermanText("lesen — to read\nMusik hören — to listen to music\nSport machen — to do sports\nFreunde treffen — to meet friends\nkochen — to cook\naufstehen — to get up / wake up\narbeiten gehen — to go to work\nfernsehen — to watch TV");
        s7_1_1.setEnglishTranslation("to read\nto listen to music\nto do sports\nto meet friends\nto cook\nto get up / wake up\nto go to work\nto watch TV");
        s7_1_1.setExplanation("These verbs and verb phrases describe daily leisure and routine activities. Notice that 'aufstehen' and 'fernsehen' are separable verbs.");
        s7_1_1.setVocabulary("lesen (to read) | Musik hören (to listen to music) | Sport machen (to do sports) | Freunde treffen (to meet friends) | kochen (to cook) | aufstehen (to get up / wake up) | arbeiten gehen (to go to work) | fernsehen (to watch TV)");
        s7_1_1.setGrammarRule("Separable Verbs in Main Clauses:\n• aufstehen -> Ich stehe um 7 Uhr auf.\n• fernsehen -> Er sieht abends fern.");
        s7_1_1.setExamples("Ich koche gern am Abend. (I like to cook in the evening.)\nIch treffe am Wochenende Freunde. (I meet friends on the weekend.)\nEr geht jeden Tag arbeiten. (He goes to work every day.)");
        s7_1_1.setImportantNote("'Sport machen' is the standard German expression for 'to do / play sports' or 'to exercise'.");
        lesson7_1.addSlide(s7_1_1);
        topic7.addLesson(lesson7_1);

        Lesson lesson7_2 = new Lesson("Fragen & Antworten: Hobbys & Tagesablauf", "Fragen & Antworten zum Alltag", "Typische Fragen und Antworten über Freizeit, Hobbys und die Aufstehzeit.", 2, 10, topic7);
        Slide s7_2_1 = new Slide("Über Hobbys & Freizeit sprechen (Talking About Hobbies)", 1, "DIALOGUE", lesson7_2);
        s7_2_1.setGermanText("Was sind deine Hobbys?\nMeine Hobbys sind Lesen und Kochen.\n\nWas machst du in deiner Freizeit?\nIch spiele Fußball und treffe Freunde.");
        s7_2_1.setEnglishTranslation("What are your hobbies?\nMy hobbies are reading and cooking.\n\nWhat do you do in your free time?\nI play football and meet friends.");
        s7_2_1.setExplanation("Use 'Was sind deine Hobbys?' or 'Was machst du in deiner Freizeit?' to ask about someone's interests. When verbs like 'lesen' and 'kochen' become nouns representing hobbies, they are capitalized: 'Lesen', 'Kochen'.");
        s7_2_1.setVocabulary("das Hobby / die Hobbys (hobby/hobbies) | die Freizeit (free time) | Fußball spielen (to play football) | Freunde treffen (to meet friends)");
        s7_2_1.setGrammarRule("Nominalization of Verbs:\nWhen German verbs are used as activities/nouns, they become neuter and are capitalized:\n• lesen -> das Lesen (reading)\n• kochen -> das Kochen (cooking)");
        s7_2_1.setExamples("A: Was sind deine Hobbys?\nB: Meine Hobbys sind Lesen und Kochen.\nA: Was machst du am Wochenende in deiner Freizeit?\nB: Ich spiele Fußball und treffe Freunde.");
        s7_2_1.setImportantNote("Notice 'in deiner Freizeit' uses the dative form 'deiner' because 'in' with a time frame takes the dative case.");
        lesson7_2.addSlide(s7_2_1);

        Slide s7_2_2 = new Slide("Tagesablauf & Aufstehen (Daily Routine & Waking Up)", 2, "DIALOGUE", lesson7_2);
        s7_2_2.setGermanText("Wann stehst du morgens auf?\nIch stehe um 7 Uhr auf.");
        s7_2_2.setEnglishTranslation("When do you get up in the morning?\nI get up at 7 o'clock.");
        s7_2_2.setExplanation("In the separable verb 'aufstehen' (to get up), the prefix 'auf' moves to the end of both questions and statements in the present tense.");
        s7_2_2.setVocabulary("wann (when) | aufstehen (to get up) | morgens (in the morning) | um ... Uhr (at ... o'clock)");
        s7_2_2.setGrammarRule("Word Order with Separable Verbs:\n• Question: Wann (1) stehst (2) du (3) morgens [auf] (End)?\n• Statement: Ich (1) stehe (2) um 7 Uhr [auf] (End).\nClock Times: Always use the preposition 'um' (um 7 Uhr = at 7 o'clock).");
        s7_2_2.setExamples("Wann stehst du morgens auf? (When do you get up in the morning?)\nIch stehe um 7 Uhr auf. (I get up at 7 o'clock.)\nAm Sonntag stehe ich spät auf. (On Sunday I get up late.)");
        s7_2_2.setImportantNote("Remember: Clock times always take 'um' (um 7 Uhr), days take 'am' (am Montag), and months take 'im' (im Mai)!");
        lesson7_2.addSlide(s7_2_2);
        topic7.addLesson(lesson7_2);
        mod1.addTopic(topic7);

        moduleRepository.save(mod1);
    }

    private void initModule2() {
        Optional<Module> existingOpt = moduleRepository.findByCode("MODULE_2");
        Module mod2;
        if (existingOpt.isPresent()) {
            mod2 = existingOpt.get();
            if (mod2.getTopics().size() == 7) {
                return; // Already seeded with the full 7-topic Module II curriculum
            }
            mod2.getTopics().clear();
        } else {
            mod2 = new Module();
            mod2.setCode("MODULE_2");
        }

        mod2.setTitle("Module II: City Life, Directions, Food & Family");
        mod2.setGermanTitle("Modul II: Stadtleben, Wegbeschreibung, Essen & Familie");
        mod2.setDescription("Master definite & indefinite articles, plural forms, negation with kein/nicht, city & street vocabulary, transportation with 'mit', asking & giving directions, informal & formal imperative, food & drinks, meals & daily meal routine, family members, and the accusative case.");
        mod2.setLevel("A1.2");
        mod2.setOrderIndex(2);
        mod2.setActive(true);

        // =========================================================================
        // TOPIC 1: Articles & Plural Forms (Artikel & Pluralformen)
        // =========================================================================
        Topic topic2_1 = new Topic(
                "Articles & Plural Forms (Artikel & Pluralformen)",
                "Artikel & Pluralformen",
                "Bestimmte und unbestimmte Artikel sowie die wichtigsten Pluralformen der Nomen.",
                1,
                mod2
        );

        // Lesson 2.1.1: Bestimmte & unbestimmte Artikel
        Lesson lesson2_1_1 = new Lesson(
                "Bestimmte & unbestimmte Artikel (Articles Overview)",
                "Bestimmte & unbestimmte Artikel",
                "Übersicht über die Artikel im Deutschen: der, die, das (bestimmt) und ein, eine, ein (unbestimmt).",
                1,
                10,
                topic2_1
        );

        Slide s2_1_1_1 = new Slide("Artikelübersicht (Definite & Indefinite Articles)", 1, "TABLE", lesson2_1_1);
        s2_1_1_1.setGermanText("German | English\nder | the — masculine\ndie | the — feminine\ndas | the — neuter\ndie | the — plural\nein | a/an — masculine\neine | a/an — feminine\nein | a/an — neuter");
        s2_1_1_1.setEnglishTranslation("der = the (masculine)\ndie = the (feminine)\ndas = the (neuter)\ndie = the (plural)\nein = a/an (masculine)\neine = a/an (feminine)\nein = a/an (neuter)");
        s2_1_1_1.setExplanation("In German, every noun has a grammatical gender: masculine (der/ein), feminine (die/eine), or neuter (das/ein). In the plural, the definite article is always 'die', and there is no indefinite article.");
        s2_1_1_1.setVocabulary("der (the - masc) | die (the - fem) | das (the - neut) | ein (a/an - masc/neut) | eine (a/an - fem)");
        s2_1_1_1.setGrammarRule("Article Summary:\n• Masculine: der / ein\n• Feminine: die / eine\n• Neuter: das / ein\n• Plural: die / — (no indefinite article)");
        s2_1_1_1.setExamples("der Mann (the man) / ein Mann (a man)\ndie Frau (the woman) / eine Frau (a woman)\ndas Kind (the child) / ein Kind (a child)");
        s2_1_1_1.setImportantNote("Always memorize every new German noun together with its definite article (der, die, or das).");
        lesson2_1_1.addSlide(s2_1_1_1);

        Slide s2_1_1_2 = new Slide("Artikelbeispiele (Article Examples)", 2, "EXAMPLE", lesson2_1_1);
        s2_1_1_2.setGermanText("der Mann — the man\ndie Frau — the woman\ndas Kind — the child\ndie Kinder — the children\nein Mann — a man\neine Frau — a woman\nein Kind — a child");
        s2_1_1_2.setEnglishTranslation("the man\nthe woman\nthe child\nthe children\na man\na woman\na child");
        s2_1_1_2.setExplanation("These basic nouns illustrate how masculine, feminine, neuter, and plural nouns pair with definite and indefinite articles.");
        s2_1_1_2.setVocabulary("der Mann (the man) | die Frau (the woman) | das Kind (the child) | die Kinder (the children)");
        s2_1_1_2.setGrammarRule("Notice:\n• 'das Kind' (singular neuter) -> 'die Kinder' (plural)\n• 'ein Kind' (a child) -> 'Kinder' (children, no article)");
        s2_1_1_2.setExamples("Hier ist ein Mann. (Here is a man.)\nDie Frau spricht Deutsch. (The woman speaks German.)\nDas Kind spielt. (The child plays.)");
        s2_1_1_2.setImportantNote("In German, all plural nouns take the definite article 'die' in the nominative case.");
        lesson2_1_1.addSlide(s2_1_1_2);
        topic2_1.addLesson(lesson2_1_1);

        // Lesson 2.1.2: Pluralformen der Nomen
        Lesson lesson2_1_2 = new Lesson(
                "Pluralformen der Nomen (Plural Forms)",
                "Pluralformen der Nomen",
                "Lerne die Pluralformen für Stadt, Verkehr, Essen und Familie.",
                2,
                12,
                topic2_1
        );

        Slide s2_1_2_1 = new Slide("Plural: Stadt, Gebäude & Verkehr (City & Transport Plurals)", 1, "TABLE", lesson2_1_2);
        s2_1_2_1.setGermanText("Singular | Plural | English\nder Mann | die Männer | man / men\ndie Frau | die Frauen | woman / women\ndas Kind | die Kinder | child / children\ndas Haus | die Häuser | house / houses\ndie Straße | die Straßen | street / streets\ndas Gebäude | die Gebäude | building / buildings\nder Bus | die Busse | bus / buses\nder Zug | die Züge | train / trains\ndas Auto | die Autos | car / cars\ndas Fahrrad | die Fahrräder | bicycle / bicycles");
        s2_1_2_1.setEnglishTranslation("der Mann -> die Männer (man / men)\ndie Frau -> die Frauen (woman / women)\ndas Kind -> die Kinder (child / children)\ndas Haus -> die Häuser (house / houses)\ndie Straße -> die Straßen (street / streets)\ndas Gebäude -> die Gebäude (building / buildings)\nder Bus -> die Busse (bus / buses)\nder Zug -> die Züge (train / trains)\ndas Auto -> die Autos (car / cars)\ndas Fahrrad -> die Fahrräder (bicycle / bicycles)");
        s2_1_2_1.setExplanation("Plural formation in German involves various patterns:\n• Umlaut + -er (Haus -> Häuser, Mann -> Männer, Fahrrad -> Fahrräder)\n• -en / -n (Frau -> Frauen, Straße -> Straßen)\n• -e (Bus -> Busse, Zug -> Züge with Umlaut)\n• -s (Auto -> Autos)\n• No change (das Gebäude -> die Gebäude)");
        s2_1_2_1.setVocabulary("das Haus / die Häuser (house/houses) | die Straße / die Straßen (street/streets) | das Gebäude / die Gebäude (building/buildings) | der Bus / die Busse (bus/buses) | der Zug / die Züge (train/trains) | das Auto / die Autos (car/cars) | das Fahrrad / die Fahrräder (bicycle/bicycles)");
        s2_1_2_1.setGrammarRule("Important: In German, the plural definite article is normally die.");
        s2_1_2_1.setExamples("Das Auto ist schnell. -> Die Autos sind schnell.\nDer Zug kommt an. -> Die Züge kommen an.");
        s2_1_2_1.setImportantNote("Notice 'das Gebäude' (the building) does not change its spelling in the plural: 'die Gebäude'.");
        lesson2_1_2.addSlide(s2_1_2_1);

        Slide s2_1_2_2 = new Slide("Plural: Essen & Familie (Food & Family Plurals)", 2, "TABLE", lesson2_1_2);
        s2_1_2_2.setGermanText("Singular | Plural | English\nder Apfel | die Äpfel | apple / apples\ndas Getränk | die Getränke | drink / drinks\ndas Essen | die Essen | meal/food / meals\ndie Familie | die Familien | family / families\nder Bruder | die Brüder | brother / brothers\ndie Schwester | die Schwestern | sister / sisters");
        s2_1_2_2.setEnglishTranslation("der Apfel -> die Äpfel (apple / apples)\ndas Getränk -> die Getränke (drink / drinks)\ndas Essen -> die Essen (meal/food / meals)\ndie Familie -> die Familien (family / families)\nder Bruder -> die Brüder (brother / brothers)\ndie Schwester -> die Schwestern (sister / sisters)");
        s2_1_2_2.setExplanation("More common plural patterns for food items and family members:\n• Umlaut change: der Apfel -> die Äpfel, der Bruder -> die Brüder\n• -e ending: das Getränk -> die Getränke\n• -n / -en ending: die Familie -> die Familien, die Schwester -> die Schwestern, das Essen -> die Essen");
        s2_1_2_2.setVocabulary("der Apfel / die Äpfel (apple/apples) | das Getränk / die Getränke (drink/drinks) | das Essen / die Essen (meal/meals) | die Familie / die Familien (family/families) | der Bruder / die Brüder (brother/brothers) | die Schwester / die Schwestern (sister/sisters)");
        s2_1_2_2.setGrammarRule("Nouns referring to male family members often form their plural with just an Umlaut (der Bruder -> die Brüder, der Vater -> die Väter).");
        s2_1_2_2.setExamples("Ich kaufe einen Apfel. -> Ich kaufe drei Äpfel.\nIch habe einen Bruder. -> Ich habe zwei Brüder.");
        s2_1_2_2.setImportantNote("Always note the vowel change: 'Bruder' with 'u' becomes 'Brüder' with 'ü'.");
        lesson2_1_2.addSlide(s2_1_2_2);
        topic2_1.addLesson(lesson2_1_2);
        mod2.addTopic(topic2_1);

        // =========================================================================
        // TOPIC 2: Negation — Kein / Nicht (Verneinung)
        // =========================================================================
        Topic topic2_2 = new Topic(
                "Negation — Kein / Nicht (Verneinung)",
                "Verneinung — Kein / Nicht",
                "Regeln und Beispiele für die Verneinung mit kein und nicht im Deutschen.",
                2,
                mod2
        );

        // Lesson 2.2.1: Verneinung mit "kein"
        Lesson lesson2_2_1 = new Lesson(
                "Verneinung mit \"kein\" (Negation with \"kein\")",
                "Verneinung mit \"kein\"",
                "Verwendung von kein, keine und keinen zur Verneinung von Nomen.",
                1,
                10,
                topic2_2
        );

        Slide s2_2_1_1 = new Slide("Regeln für \"kein\" (Rules for \"kein\")", 1, "GRAMMAR", lesson2_2_1);
        s2_2_1_1.setGermanText("Kein:\nkein is generally used to negate a noun when there is no definite article.\n\nGerman | English\nkein | no / not a / not any — masculine/neuter\nkeine | no / not a / not any — feminine/plural\nkeinen | no / not a / not any — masculine accusative");
        s2_2_1_1.setEnglishTranslation("kein = no / not a / not any (masculine / neuter nominative & neuter accusative)\nkeine = no / not a / not any (feminine & plural)\nkeinen = no / not a / not any (masculine accusative)");
        s2_2_1_1.setExplanation("'kein' declines exactly like the indefinite article 'ein'. Use 'kein' when negating a noun that would otherwise take 'ein' or has no article (plural/uncountable).");
        s2_2_1_1.setVocabulary("kein (no/not a - masc/neut) | keine (no/not any - fem/plur) | keinen (no/not a - masc acc)");
        s2_2_1_1.setGrammarRule("Declension of 'kein':\n• Masculine (Nom): kein Mann\n• Masculine (Acc): keinen Hund / keinen Bruder\n• Feminine: keine Schwester / keine Frau\n• Neuter: kein Auto / kein Haus\n• Plural: keine Kinder / keine Fragen");
        s2_2_1_1.setExamples("Das ist kein Problem. (That is no problem.)\nIch habe kein Auto. (I don't have a car.)\nIch habe keine Zeit. (I have no time.)");
        s2_2_1_1.setImportantNote("'kein' is only used to negate NOUNS, never verbs or adjectives.");
        lesson2_2_1.addSlide(s2_2_1_1);

        Slide s2_2_1_2 = new Slide("Beispielsätze mit \"kein\" (Examples with \"kein\")", 2, "EXAMPLE", lesson2_2_1);
        s2_2_1_2.setGermanText("Ich habe ein Auto.\n-> Ich habe kein Auto. (I don't have a car.)\n\nIch habe einen Hund.\n-> Ich habe keinen Hund. (I don't have a dog.)\n\nSie hat eine Schwester.\n-> Sie hat keine Schwester. (She doesn't have a sister.)\n\nWir haben Kinder.\n-> Wir haben keine Kinder. (We don't have any children.)");
        s2_2_1_2.setEnglishTranslation("Ich habe ein Auto. -> Ich habe kein Auto. (I don't have a car.)\nIch habe einen Hund. -> Ich habe keinen Hund. (I don't have a dog.)\nSie hat eine Schwester. -> Sie hat keine Schwester. (She doesn't have a sister.)\nWir haben Kinder. -> Wir haben keine Kinder. (We don't have any children.)");
        s2_2_1_2.setExplanation("Notice how 'ein' turns into 'kein', 'einen' turns into 'keinen', 'eine' turns into 'keine', and positive plural nouns take 'keine'.");
        s2_2_1_2.setVocabulary("das Auto (car) | der Hund (dog) | die Schwester (sister) | das Kind / die Kinder (child/children)");
        s2_2_1_2.setGrammarRule("Indefinite to Negative Replacement:\n• ein Auto -> kein Auto\n• einen Hund -> keinen Hund\n• eine Schwester -> keine Schwester\n• Kinder -> keine Kinder");
        s2_2_1_2.setExamples("Ich habe einen Hund. -> Ich habe keinen Hund.\nSie hat eine Schwester. -> Sie hat keine Schwester.\nWir haben Kinder. -> Wir haben keine Kinder.");
        s2_2_1_2.setImportantNote("In the accusative case, masculine nouns take 'keinen' (keinen Hund, keinen Bruder, keinen Apfel).");
        lesson2_2_1.addSlide(s2_2_1_2);
        topic2_2.addLesson(lesson2_2_1);

        // Lesson 2.2.2: Verneinung mit "nicht" & Vergleich
        Lesson lesson2_2_2 = new Lesson(
                "Verneinung mit \"nicht\" & Vergleich (Negation with \"nicht\")",
                "Verneinung mit \"nicht\" & Vergleich",
                "Verneinung von Verben, Adjektiven, Adverbien und die einfache Unterscheidungsregel.",
                2,
                10,
                topic2_2
        );

        Slide s2_2_2_1 = new Slide("Verwendung von \"nicht\" (Using \"nicht\")", 1, "EXAMPLE", lesson2_2_2);
        s2_2_2_1.setGermanText("nicht = not\nIt is commonly used to negate verbs, adjectives, adverbs, or specific information.\n\nGerman | English\nDas ist nicht gut. | That is not good.\nIch bin nicht müde. | I am not tired.\nIch wohne nicht in Berlin. | I don't live in Berlin.\nEr trinkt nicht Kaffee. | He does not drink coffee.\nDas Essen ist nicht teuer. | The food is not expensive.\nIch fahre heute nicht. | I am not travelling today.");
        s2_2_2_1.setEnglishTranslation("Das ist nicht gut. (That is not good.)\nIch bin nicht müde. (I am not tired.)\nIch wohne nicht in Berlin. (I don't live in Berlin.)\nEr trinkt nicht Kaffee. (He does not drink coffee.)\nDas Essen ist nicht teuer. (The food is not expensive.)\nIch fahre heute nicht. (I am not travelling today.)");
        s2_2_2_1.setExplanation("'nicht' negates entire sentences, actions (verbs), descriptions (adjectives), time (adverbs), or specific prepositional phrases (in Berlin).");
        s2_2_2_1.setVocabulary("nicht (not) | gut (good) | müde (tired) | teuer (expensive) | heute (today) | trinken (to drink) | fahren (to travel/drive)");
        s2_2_2_1.setGrammarRule("Position of 'nicht':\n• Before adjectives: nicht gut, nicht müde, nicht teuer\n• Before prepositions: nicht in Berlin\n• At the end of simple clauses: Ich fahre heute nicht.");
        s2_2_2_1.setExamples("Das ist nicht gut. (That is not good.)\nIch bin nicht müde. (I am not tired.)\nDas Essen ist nicht teuer. (The food is not expensive.)");
        s2_2_2_1.setImportantNote("'nicht' is used with definite nouns too (e.g. Ich sehe den Bus nicht).");
        lesson2_2_2.addSlide(s2_2_2_1);

        Slide s2_2_2_2 = new Slide("Kein vs. Nicht (The Golden Rule)", 2, "GRAMMAR", lesson2_2_2);
        s2_2_2_2.setGermanText("Kein vs. Nicht\n\nGerman | English\nIch habe kein Auto. | I don't have a car.\nIch fahre nicht. | I am not travelling/driving.\nDas ist kein Haus. | That is not a house.\nDas Haus ist nicht groß. | The house is not big.\n\nEasy rule:\n• kein -> noun\n• nicht -> verb / adjective / adverb or other sentence element");
        s2_2_2_2.setEnglishTranslation("Ich habe kein Auto. (I don't have a car.)\nIch fahre nicht. (I am not travelling/driving.)\nDas ist kein Haus. (That is not a house.)\nDas Haus ist nicht groß. (The house is not big.)\n\nEasy rule:\n• kein -> noun\n• nicht -> verb / adjective / adverb or other sentence element");
        s2_2_2_2.setExplanation("To decide between 'kein' and 'nicht': ask yourself what is being negated. If you are negating a noun with 'a/an' or no article, use 'kein'. For everything else (actions, descriptions, locations, time), use 'nicht'.");
        s2_2_2_2.setVocabulary("groß (big/large) | das Haus (house) | fahren (to travel/drive)");
        s2_2_2_2.setGrammarRule("The Golden Negation Rule:\n• Negating a noun -> kein / keine / keinen\n• Negating a verb / adjective / adverb / definite noun -> nicht");
        s2_2_2_2.setExamples("Das ist kein Auto. (That is not a car. -> noun)\nDas Auto ist nicht schnell. (The car is not fast. -> adjective)\nIch fahre nicht. (I am not driving. -> verb)");
        s2_2_2_2.setImportantNote("Compare: 'Das ist kein Haus' (noun negation) vs. 'Das Haus ist nicht groß' (adjective negation).");
        lesson2_2_2.addSlide(s2_2_2_2);
        topic2_2.addLesson(lesson2_2_2);
        mod2.addTopic(topic2_2);

        // =========================================================================
        // TOPIC 3: City Life & Places (Stadtleben & Orte)
        // =========================================================================
        Topic topic2_3 = new Topic(
                "City Life & Places (Stadtleben & Orte)",
                "Stadtleben & Orte",
                "Vokabular für Gebäude, Geschäfte, Einrichtungen, Straßen und Richtungen in der Stadt.",
                3,
                mod2
        );

        // Lesson 2.3.1: Gebäude & Einrichtungen
        Lesson lesson2_3_1 = new Lesson(
                "Gebäude & Einrichtungen (Buildings & Institutions)",
                "Gebäude & Einrichtungen",
                "Vokabeln für öffentliche Gebäude, Geschäfte und Dienstleistungen in der Stadt.",
                1,
                10,
                topic2_3
        );

        Slide s2_3_1_1 = new Slide("Öffentliche Gebäude & Geschäfte (Public Buildings)", 1, "VOCABULARY", lesson2_3_1);
        s2_3_1_1.setGermanText("die Stadt — city\ndas Gebäude — building\ndas Haus — house\ndie Wohnung — apartment\ndas Geschäft — shop/store\nder Supermarkt — supermarket\ndie Schule — school\ndie Universität — university\ndas Krankenhaus — hospital\ndie Apotheke — pharmacy\ndie Bank — bank\ndie Post — post office");
        s2_3_1_1.setEnglishTranslation("the city\nthe building\nthe house\nthe apartment\nthe shop/store\nthe supermarket\nthe school\nthe university\nthe hospital\nthe pharmacy\nthe bank\nthe post office");
        s2_3_1_1.setExplanation("These core nouns cover the most important everyday buildings and services in a German city.");
        s2_3_1_1.setVocabulary("die Stadt (city) | das Gebäude (building) | das Haus (house) | die Wohnung (apartment) | das Geschäft (shop) | der Supermarkt (supermarket) | die Schule (school) | die Universität (university) | das Krankenhaus (hospital) | die Apotheke (pharmacy) | die Bank (bank) | die Post (post office)");
        s2_3_1_1.setGrammarRule("Notice genders:\n• Masculine: der Supermarkt\n• Feminine: die Stadt, die Wohnung, die Schule, die Universität, die Apotheke, die Bank, die Post\n• Neuter: das Gebäude, das Haus, das Geschäft, das Krankenhaus");
        s2_3_1_1.setExamples("Wo ist der Supermarkt? (Where is the supermarket?)\nDie Schule ist neu. (The school is new.)\nDas Krankenhaus ist groß. (The hospital is big.)");
        s2_3_1_1.setImportantNote("Notice that 'Krankenhaus' literally means 'sick people house' (krank + Haus).");
        lesson2_3_1.addSlide(s2_3_1_1);

        Slide s2_3_1_2 = new Slide("Freizeit-, Kultur- & Verkehrsorte (Leisure & Transit Places)", 2, "VOCABULARY", lesson2_3_1);
        s2_3_1_2.setGermanText("das Restaurant — restaurant\ndas Café — café\ndas Hotel — hotel\ndas Museum — museum\ndas Kino — cinema\ndie Bibliothek — library\nder Bahnhof — railway station\nder Flughafen — airport");
        s2_3_1_2.setEnglishTranslation("the restaurant\nthe café\nthe hotel\nthe museum\nthe cinema\nthe library\nthe railway station\nthe airport");
        s2_3_1_2.setExplanation("Essential vocabulary for cultural attractions, leisure venues, and transit hubs.");
        s2_3_1_2.setVocabulary("das Restaurant (restaurant) | das Café (café) | das Hotel (hotel) | das Museum (museum) | das Kino (cinema) | die Bibliothek (library) | der Bahnhof (railway station) | der Flughafen (airport)");
        s2_3_1_2.setGrammarRule("Transit hubs:\n• der Bahnhof (railway station) — masculine\n• der Flughafen (airport) — masculine\nMany international loanwords are neuter: das Restaurant, das Café, das Hotel, das Museum, das Kino.");
        s2_3_1_2.setExamples("Wo ist der Bahnhof? (Where is the railway station?)\nWir gehen ins Kino. (We are going to the cinema.)\nDas Restaurant ist gut. (The restaurant is good.)");
        s2_3_1_2.setImportantNote("'der Bahnhof' (Bahn + Hof) is masculine (der Bahnhof).");
        lesson2_3_1.addSlide(s2_3_1_2);
        topic2_3.addLesson(lesson2_3_1);

        // Lesson 2.3.2: Straßen, Orte & Ortsangaben
        Lesson lesson2_3_2 = new Lesson(
                "Straßen, Orte & Ortsangaben (Streets & Locations)",
                "Straßen, Orte & Ortsangaben",
                "Straßenmerkmale, Plätze und räumliche Richtungs- und Positionsangaben.",
                2,
                10,
                topic2_3
        );

        Slide s2_3_2_1 = new Slide("Straßen & Plätze (Streets & Places)", 1, "VOCABULARY", lesson2_3_2);
        s2_3_2_1.setGermanText("die Straße — street\ndie Hauptstraße — main street\ndie Kreuzung — intersection\ndie Ecke — corner\nder Platz — square/place\ndie Ampel — traffic light\nder Kreisverkehr — roundabout\nder Park — park\ndie Brücke — bridge\nder Weg — way/path\ndie Bushaltestelle — bus stop\nder Bahnhof — railway station\ndas Zentrum — city centre\ndie Innenstadt — city centre/downtown");
        s2_3_2_1.setEnglishTranslation("the street\nthe main street\nthe intersection\nthe corner\nthe square/place\nthe traffic light\nthe roundabout\nthe park\nthe bridge\nthe way/path\nthe bus stop\nthe railway station\nthe city centre\nthe city centre/downtown");
        s2_3_2_1.setExplanation("These navigation landmarks are essential when giving and asking for directions in a town or city.");
        s2_3_2_1.setVocabulary("die Straße (street) | die Kreuzung (intersection) | die Ampel (traffic light) | der Kreisverkehr (roundabout) | der Park (park) | die Brücke (bridge) | die Bushaltestelle (bus stop) | das Zentrum (city centre)");
        s2_3_2_1.setGrammarRule("Compound nouns inherit the gender of the LAST element:\n• die Haupt + die Straße = die Hauptstraße\n• der Bus + die Haltestelle = die Bushaltestelle\n• der Kreis + der Verkehr = der Kreisverkehr");
        s2_3_2_1.setExamples("Gehen Sie bis zur Ampel. (Go up to the traffic light.)\nDie Bushaltestelle ist an der Ecke. (The bus stop is at the corner.)");
        s2_3_2_1.setImportantNote("'die Innenstadt' and 'das Zentrum' both refer to the city centre/downtown.");
        lesson2_3_2.addSlide(s2_3_2_1);

        Slide s2_3_2_2 = new Slide("Richtungen & Positionen (Directions & Spatial Words)", 2, "VOCABULARY", lesson2_3_2);
        s2_3_2_2.setGermanText("links — left\nrechts — right\ngeradeaus — straight ahead\nhier — here\ndort — there\nneben — next to\ngegenüber — opposite\nzwischen — between\nvor — in front of\nhinter — behind");
        s2_3_2_2.setEnglishTranslation("left\nright\nstraight ahead\nhere\nthere\nnext to\nopposite\nbetween\nin front of\nbehind");
        s2_3_2_2.setExplanation("These words specify directions of movement (links, rechts, geradeaus) and relative spatial positions (neben, gegenüber, zwischen, vor, hinter).");
        s2_3_2_2.setVocabulary("links (left) | rechts (right) | geradeaus (straight ahead) | hier (here) | dort (there) | neben (next to) | gegenüber (opposite) | zwischen (between) | vor (in front of) | hinter (behind)");
        s2_3_2_2.setGrammarRule("Prepositions of Place:\n• neben dem Bahnhof (next to the railway station)\n• gegenüber dem Hotel (opposite the hotel)\n• zwischen der Schule und der Bank (between the school and the bank)");
        s2_3_2_2.setExamples("Das Hotel ist hier. (The hotel is here.)\nDas Museum ist dort. (The museum is there.)\nBiegen Sie links ab. (Turn left.)\nGehen Sie geradeaus. (Go straight ahead.)");
        s2_3_2_2.setImportantNote("'gegenüber' often follows or precedes a dative noun: 'gegenüber dem Hotel' (opposite the hotel).");
        lesson2_3_2.addSlide(s2_3_2_2);
        topic2_3.addLesson(lesson2_3_2);
        mod2.addTopic(topic2_3);

        // =========================================================================
        // TOPIC 4: Transportation (Verkehrsmittel & Reisen)
        // =========================================================================
        Topic topic2_4 = new Topic(
                "Transportation (Verkehrsmittel & Reisen)",
                "Verkehrsmittel",
                "Vokabular für Verkehrsmittel und das Bilden von Reisesätzen mit der Präposition \"mit\".",
                4,
                mod2
        );

        // Lesson 2.4.1: Verkehrsmittel Vokabular
        Lesson lesson2_4_1 = new Lesson(
                "Verkehrsmittel Vokabular (Means of Transport)",
                "Verkehrsmittel Vokabular",
                "Lerne die deutschen Bezeichnungen für Bus, Zug, Auto, Fahrrad, U-Bahn und mehr.",
                1,
                8,
                topic2_4
        );

        Slide s2_4_1_1 = new Slide("Die Verkehrsmittel (Means of Transport)", 1, "VOCABULARY", lesson2_4_1);
        s2_4_1_1.setGermanText("der Bus — bus\nder Zug — train\ndas Auto — car\ndas Fahrrad — bicycle\ndas Motorrad — motorcycle\ndas Taxi — taxi\ndie U-Bahn — underground/subway\ndie Straßenbahn — tram\ndas Flugzeug — aeroplane\ndas Schiff — ship\nzu Fuß — on foot");
        s2_4_1_1.setEnglishTranslation("the bus\nthe train\nthe car\nthe bicycle\nthe motorcycle\nthe taxi\nthe underground/subway\nthe tram\nthe aeroplane\nthe ship\non foot");
        s2_4_1_1.setExplanation("All primary modes of public and private transit in German.");
        s2_4_1_1.setVocabulary("der Bus (bus) | der Zug (train) | das Auto (car) | das Fahrrad (bicycle) | das Motorrad (motorcycle) | das Taxi (taxi) | die U-Bahn (underground) | die Straßenbahn (tram) | das Flugzeug (aeroplane) | das Schiff (ship) | zu Fuß (on foot)");
        s2_4_1_1.setGrammarRule("Gender breakdown:\n• Masculine: der Bus, der Zug\n• Neuter: das Auto, das Fahrrad, das Motorrad, das Taxi, das Flugzeug, das Schiff\n• Feminine: die U-Bahn, die Straßenbahn");
        s2_4_1_1.setExamples("Der Bus kommt um 8 Uhr. (The bus comes at 8 o'clock.)\nIch nehme das Fahrrad. (I take the bicycle.)\nWir gehen zu Fuß. (We walk / go on foot.)");
        s2_4_1_1.setImportantNote("'zu Fuß' is a set prepositional phrase meaning 'on foot' (walking).");
        lesson2_4_1.addSlide(s2_4_1_1);
        topic2_4.addLesson(lesson2_4_1);

        // Lesson 2.4.2: Unterwegs mit "mit"
        Lesson lesson2_4_2 = new Lesson(
                "Unterwegs mit \"mit\" (Traveling with \"mit\")",
                "Reisesätze mit \"mit\"",
                "Sätze für die Fortbewegung mit der Dativ-Präposition \"mit\" bilden.",
                2,
                10,
                topic2_4
        );

        Slide s2_4_2_1 = new Slide("Sätze mit \"mit\" (Sentences with \"mit\")", 1, "EXAMPLE", lesson2_4_2);
        s2_4_2_1.setGermanText("Ich fahre mit dem Bus. — I travel by bus.\nIch fahre mit dem Zug. — I travel by train.\nIch fahre mit dem Auto. — I travel by car.\nIch fahre mit dem Fahrrad. — I cycle.\nIch fahre mit der U-Bahn. — I travel by underground.\nIch fahre mit der Straßenbahn. — I travel by tram.\nIch fahre mit dem Taxi. — I travel by taxi.\nWir fahren mit dem Schiff. — We travel by ship.\nWir gehen zu Fuß. — We walk.");
        s2_4_2_1.setEnglishTranslation("Ich fahre mit dem Bus. (I travel by bus.)\nIch fahre mit dem Zug. (I travel by train.)\nIch fahre mit dem Auto. (I travel by car.)\nIch fahre mit dem Fahrrad. (I cycle.)\nIch fahre mit der U-Bahn. (I travel by underground.)\nIch fahre mit der Straßenbahn. (I travel by tram.)\nIch fahre mit dem Taxi. (I travel by taxi.)\nWir fahren mit dem Schiff. (We travel by ship.)\nWir gehen zu Fuß. (We walk.)");
        s2_4_2_1.setExplanation("To express traveling by a means of transport, use the verb 'fahren' + preposition 'mit' + Dative article:\n• mit + der Bus -> mit dem Bus\n• mit + das Auto -> mit dem Auto\n• mit + die U-Bahn -> mit der U-Bahn");
        s2_4_2_1.setVocabulary("fahren (to travel/drive) | gehen (to go/walk) | mit (with / by means of)");
        s2_4_2_1.setGrammarRule("Preposition 'mit' always takes the Dative case:\n• Masculine/Neuter: mit dem (Bus, Zug, Auto, Fahrrad, Taxi, Schiff)\n• Feminine: mit der (U-Bahn, Straßenbahn)\n• Walking: zu Fuß gehen (no 'mit')");
        s2_4_2_1.setExamples("Ich fahre mit dem Bus zur Arbeit. (I travel by bus to work.)\nWir fahren mit der U-Bahn ins Zentrum. (We travel by underground to the city centre.)\nWir gehen zu Fuß. (We walk.)");
        s2_4_2_1.setImportantNote("Use 'fahren mit dem/der...', but for walking say 'zu Fuß gehen'.");
        lesson2_4_2.addSlide(s2_4_2_1);
        topic2_4.addLesson(lesson2_4_2);
        mod2.addTopic(topic2_4);

        // =========================================================================
        // TOPIC 5: Directions & The Imperative (Wegbeschreibung & Imperativ)
        // =========================================================================
        Topic topic2_5 = new Topic(
                "Directions & The Imperative (Wegbeschreibung & Imperativ)",
                "Wegbeschreibung & Imperativ",
                "Nach dem Weg fragen, den Weg beschreiben und den Imperativ (Befehlsform) anwenden.",
                5,
                mod2
        );

        // Lesson 2.5.1: Nach dem Weg fragen & Wegbeschreibung
        Lesson lesson2_5_1 = new Lesson(
                "Nach dem Weg fragen & Wegbeschreibung (Directions Dialogues)",
                "Nach dem Weg fragen & Wegbeschreibung",
                "Fragen nach dem Weg und typische Antworten zur Wegbeschreibung.",
                1,
                10,
                topic2_5
        );

        Slide s2_5_1_1 = new Slide("Nach dem Weg fragen (Asking for Directions)", 1, "DIALOGUE", lesson2_5_1);
        s2_5_1_1.setGermanText("Wo ist der Bahnhof? — Where is the railway station?\nWo ist die Bushaltestelle? — Where is the bus stop?\nWo ist das Hotel? — Where is the hotel?\nWo ist die Apotheke? — Where is the pharmacy?\nWo ist der Supermarkt? — Where is the supermarket?\nWie komme ich zum Bahnhof? — How do I get to the railway station?\nWie komme ich zum Hotel? — How do I get to the hotel?\nWie komme ich zur Schule? — How do I get to the school?\nIst das weit? — Is it far?\nIst das in der Nähe? — Is it nearby?\nKönnen Sie mir helfen? — Can you help me?\nKönnen Sie mir den Weg zeigen? — Can you show me the way?");
        s2_5_1_1.setEnglishTranslation("Where is the railway station?\nWhere is the bus stop?\nWhere is the hotel?\nWhere is the pharmacy?\nWhere is the supermarket?\nHow do I get to the railway station?\nHow do I get to the hotel?\nHow do I get to the school?\nIs it far?\nIs it nearby?\nCan you help me?\nCan you show me the way?");
        s2_5_1_1.setExplanation("Use 'Wo ist...?' or 'Wie komme ich zu...?' to ask for directions politely. 'Können Sie mir helfen?' is a courteous way to ask strangers for assistance.");
        s2_5_1_1.setVocabulary("wie komme ich (how do I get) | zum (to the - masc/neut) | zur (to the - fem) | weit (far) | in der Nähe (nearby) | helfen (to help) | zeigen (to show)");
        s2_5_1_1.setGrammarRule("Direction Prepositions:\n• zu + dem = zum (zum Bahnhof, zum Hotel, zum Supermarkt)\n• zu + der = zur (zur Schule, zur Apotheke, zur Bushaltestelle)");
        s2_5_1_1.setExamples("Entschuldigung, wie komme ich zum Bahnhof?\nIst das Hotel in der Nähe?\nKönnen Sie mir den Weg zeigen?");
        s2_5_1_1.setImportantNote("Notice the contraction: 'zum' for masculine/neuter, 'zur' for feminine nouns.");
        lesson2_5_1.addSlide(s2_5_1_1);

        Slide s2_5_1_2 = new Slide("Wegbeschreibung geben (Giving Directions)", 2, "EXAMPLE", lesson2_5_1);
        s2_5_1_2.setGermanText("Gehen Sie geradeaus. — Go straight ahead.\nGehen Sie nach links. — Go to the left.\nGehen Sie nach rechts. — Go to the right.\nBiegen Sie links ab. — Turn left.\nBiegen Sie rechts ab. — Turn right.\nNehmen Sie die erste Straße links. — Take the first street on the left.\nNehmen Sie die zweite Straße rechts. — Take the second street on the right.\nGehen Sie bis zur Ampel. — Go up to the traffic light.\nGehen Sie bis zur Kreuzung. — Go up to the intersection.\nDas ist neben dem Bahnhof. — It is next to the railway station.\nDas ist gegenüber dem Hotel. — It is opposite the hotel.\nDas ist zwischen der Schule und der Bank. — It is between the school and the bank.\nDas ist nicht weit. — It is not far.\nDas ist ganz in der Nähe. — It is very close/nearby.");
        s2_5_1_2.setEnglishTranslation("Gehen Sie geradeaus. (Go straight ahead.)\nGehen Sie nach links. (Go to the left.)\nGehen Sie nach rechts. (Go to the right.)\nBiegen Sie links ab. (Turn left.)\nBiegen Sie rechts ab. (Turn right.)\nNehmen Sie die erste Straße links. (Take the first street on the left.)\nNehmen Sie die zweite Straße rechts. (Take the second street on the right.)\nGehen Sie bis zur Ampel. (Go up to the traffic light.)\nGehen Sie bis zur Kreuzung. (Go up to the intersection.)\nDas ist neben dem Bahnhof. (It is next to the railway station.)\nDas ist gegenüber dem Hotel. (It is opposite the hotel.)\nDas ist zwischen der Schule und der Bank. (It is between the school and the bank.)\nDas ist nicht weit. (It is not far.)\nDas ist ganz in der Nähe. (It is very close/nearby.)");
        s2_5_1_2.setExplanation("Key instructions for guiding someone through a city with distances, turns, and landmark positions.");
        s2_5_1_2.setVocabulary("geradeaus (straight ahead) | abbiegen (to turn) | die erste Straße (first street) | die zweite Straße (second street) | bis zu (up to) | neben (next to) | gegenüber (opposite) | zwischen (between)");
        s2_5_1_2.setGrammarRule("Separable verb 'abbiegen':\n• Biegen Sie links ab! (Prefix 'ab' moves to the end)");
        s2_5_1_2.setExamples("Gehen Sie geradeaus und biegen Sie links ab.\nDas Hotel ist neben dem Bahnhof, ganz in der Nähe.");
        s2_5_1_2.setImportantNote("'erste' = first, 'zweite' = second.");
        lesson2_5_1.addSlide(s2_5_1_2);
        topic2_5.addLesson(lesson2_5_1);

        // Lesson 2.5.2: Der Imperativ
        Lesson lesson2_5_2 = new Lesson(
                "Der Imperativ (The Imperative Mood)",
                "Der Imperativ (Befehlsform)",
                "Bildung des Imperativs für Befehle, Anweisungen, Bitten und Richtungsangaben.",
                2,
                10,
                topic2_5
        );

        Slide s2_5_2_1 = new Slide("Imperativ-Formen (Common Imperative Forms)", 1, "TABLE", lesson2_5_2);
        s2_5_2_1.setGermanText("Infinitive | Imperative | English\nkommen | Komm! | Come!\ngehen | Geh! | Go!\nwarten | Warte! | Wait!\nhören | Hör! | Listen!\nmachen | Mach! | Do!/Make!\nnehmen | Nimm! | Take!\nfahren | Fahr! | Drive!/Go!\nlesen | Lies! | Read!\nsprechen | Sprich! | Speak!\nessen | Iss! | Eat!\ntrinken | Trink! | Drink!\nsehen | Sieh! | Look/See!");
        s2_5_2_1.setEnglishTranslation("kommen -> Komm! (Come!)\ngehen -> Geh! (Go!)\nwarten -> Warte! (Wait!)\nhören -> Hör! (Listen!)\nmachen -> Mach! (Do!/Make!)\nnehmen -> Nimm! (Take!)\nfahren -> Fahr! (Drive!/Go!)\nlesen -> Lies! (Read!)\nsprechen -> Sprich! (Speak!)\nessen -> Iss! (Eat!)\ntrinken -> Trink! (Drink!)\nsehen -> Sieh! (Look/See!)");
        s2_5_2_1.setExplanation("The imperative is used for commands, instructions, requests, and giving directions.\nTo form the informal singular imperative (du): take the verb stem and drop '-st' and the pronoun 'du'.\nVerbs with vowel change (e -> i/ie) retain the vowel change in the imperative:\n• nehmen -> Nimm!\n• lesen -> Lies!\n• sprechen -> Sprich!\n• essen -> Iss!\n• sehen -> Sieh!");
        s2_5_2_1.setVocabulary("Komm! (Come!) | Geh! (Go!) | Warte! (Wait!) | Hör! (Listen!) | Mach! (Do/Make!) | Nimm! (Take!) | Fahr! (Drive/Go!) | Lies! (Read!) | Sprich! (Speak!) | Iss! (Eat!) | Trink! (Drink!) | Sieh! (Look!)");
        s2_5_2_1.setGrammarRule("Imperative Rules (du-form):\n• Regular verbs: drop '-en' (Komm!, Geh!, Hör!, Mach!, Trink!)\n• Verbs ending in -t/-d: add -e (Warte!)\n• Stem-changing verbs (e -> i/ie): adopt the changed stem without ending (Lies!, Sprich!, Iss!, Sieh!, Nimm!)");
        s2_5_2_1.setExamples("Komm hierher! (Come here!)\nWarte einen Moment! (Wait a moment!)\nLies das Buch! (Read the book!)");
        s2_5_2_1.setImportantNote("Notice that no pronoun ('du') is used in informal singular commands (Geh!, not *Geh du!).");
        lesson2_5_2.addSlide(s2_5_2_1);

        Slide s2_5_2_2 = new Slide("Imperativ für Richtungen: Informell & Formell (Direction Commands)", 2, "EXAMPLE", lesson2_5_2);
        s2_5_2_2.setGermanText("Informal Imperative (du):\nGeh geradeaus! — Go straight ahead!\nGeh nach links! — Go to the left!\nGeh nach rechts! — Go to the right!\nBieg links ab! — Turn left!\nBieg rechts ab! — Turn right!\nWarte hier! — Wait here!\nFahr geradeaus! — Drive straight ahead!\nNimm die erste Straße! — Take the first street!\nNimm den Bus! — Take the bus!\nGeh zum Bahnhof! — Go to the railway station!\n\nFormal Imperative (Sie):\nGehen Sie geradeaus. — Go straight ahead.\nBiegen Sie links ab. — Turn left.\nBiegen Sie rechts ab. — Turn right.\nWarten Sie hier. — Wait here.\nNehmen Sie den Bus. — Take the bus.\nFahren Sie geradeaus. — Drive straight ahead.");
        s2_5_2_2.setEnglishTranslation("Informal:\nGeh geradeaus! (Go straight ahead!)\nBieg links ab! (Turn left!)\nWarte hier! (Wait here!)\nFahr geradeaus! (Drive straight ahead!)\nNimm die erste Straße! (Take the first street!)\nNimm den Bus! (Take the bus!)\nGeh zum Bahnhof! (Go to the railway station!)\n\nFormal:\nGehen Sie geradeaus. (Go straight ahead.)\nBiegen Sie links ab. (Turn left.)\nBiegen Sie rechts ab. (Turn right.)\nWarten Sie hier. (Wait here.)\nNehmen Sie den Bus. (Take the bus.)\nFahren Sie geradeaus. (Drive straight ahead.)");
        s2_5_2_2.setExplanation("When giving directions to a stranger or in a polite setting, use the formal imperative with 'Sie' (verb infinitive + Sie: Gehen Sie, Biegen Sie ab, Nehmen Sie). With friends or family, use the short informal form (Geh!, Bieg ab!, Nimm!).");
        s2_5_2_2.setVocabulary("abbiegen (to turn) | nehmen (to take) | der Bus (bus) | der Bahnhof (railway station)");
        s2_5_2_2.setGrammarRule("Formal Imperative Pattern:\n[Infinitive Verb] + Sie + [Rest]\n• Gehen Sie geradeaus.\n• Biegen Sie links ab.\n• Nehmen Sie den Bus.");
        s2_5_2_2.setExamples("Formal: Nehmen Sie den Bus zum Bahnhof.\nInformal: Nimm den Bus zum Bahnhof!");
        s2_5_2_2.setImportantNote("For directions to a stranger, 'Sie' is commonly used (Gehen Sie, Biegen Sie ab).");
        lesson2_5_2.addSlide(s2_5_2_2);
        topic2_5.addLesson(lesson2_5_2);
        mod2.addTopic(topic2_5);

        // =========================================================================
        // TOPIC 6: Food, Meals & Daily Routine (Essen, Mahlzeiten & Essensroutine)
        // =========================================================================
        Topic topic2_6 = new Topic(
                "Food, Meals & Daily Routine (Essen, Mahlzeiten & Essensroutine)",
                "Essen, Mahlzeiten & Essensroutine",
                "Vokabular für Lebensmittel, Getränke, Mahlzeiten und die tägliche Essensroutine.",
                6,
                mod2
        );

        // Lesson 2.6.1: Lebensmittel & Getränke
        Lesson lesson2_6_1 = new Lesson(
                "Lebensmittel & Getränke (Food & Drinks)",
                "Lebensmittel & Getränke",
                "Wortschatz für Essen, Zutaten, Obst, Gemüse und Getränke.",
                1,
                10,
                topic2_6
        );

        Slide s2_6_1_1 = new Slide("Lebensmittel (Food Vocabulary)", 1, "VOCABULARY", lesson2_6_1);
        s2_6_1_1.setGermanText("das Essen — food/meal\ndas Brot — bread\ndas Brötchen — bread roll\nder Reis — rice\ndie Nudeln — noodles/pasta\ndie Kartoffel — potato\ndas Gemüse — vegetables\ndas Obst — fruit\nder Apfel — apple\ndie Banane — banana\ndie Orange — orange\ndie Tomate — tomato\nder Salat — salad\ndie Suppe — soup\ndas Fleisch — meat\ndas Hähnchen — chicken\nder Fisch — fish\ndas Ei — egg\nder Käse — cheese\ndie Pizza — pizza\nder Kuchen — cake\ndas Sandwich — sandwich");
        s2_6_1_1.setEnglishTranslation("the food/meal\nthe bread\nthe bread roll\nthe rice\nthe noodles/pasta\nthe potato\nthe vegetables\nthe fruit\nthe apple\nthe banana\nthe orange\nthe tomato\nthe salad\nthe soup\nthe meat\nthe chicken\nthe fish\nthe egg\nthe cheese\nthe pizza\nthe cake\nthe sandwich");
        s2_6_1_1.setExplanation("Comprehensive vocabulary for common food staples, fruits, vegetables, and dishes in German.");
        s2_6_1_1.setVocabulary("das Brot (bread) | das Brötchen (bread roll) | der Reis (rice) | die Nudeln (pasta) | die Kartoffel (potato) | das Gemüse (vegetables) | das Obst (fruit) | der Apfel (apple) | die Banane (banana) | der Salat (salad) | die Suppe (soup) | das Fleisch (meat) | der Fisch (fish) | der Käse (cheese) | die Pizza (pizza) | der Kuchen (cake)");
        s2_6_1_1.setGrammarRule("Genders of Foods:\n• Masculine: der Reis, der Apfel, der Salat, der Fisch, der Käse, der Kuchen\n• Feminine: die Nudeln (pl), die Kartoffel, die Banane, die Orange, die Tomate, die Suppe, die Pizza\n• Neuter: das Essen, das Brot, das Brötchen, das Gemüse, das Obst, das Fleisch, das Hähnchen, das Ei, das Sandwich");
        s2_6_1_1.setExamples("Ich esse gern Brot und Käse. (I like eating bread and cheese.)\nDer Apfel ist frisch. (The apple is fresh.)\nDie Pizza schmeckt gut. (The pizza tastes good.)");
        s2_6_1_1.setImportantNote("Notice diminutive ending '-chen': 'das Brötchen' (little bread) is neuter (das), as are all nouns ending in -chen.");
        lesson2_6_1.addSlide(s2_6_1_1);

        Slide s2_6_1_2 = new Slide("Getränke & Sätze (Drinks & Useful Sentences)", 2, "EXAMPLE", lesson2_6_1);
        s2_6_1_2.setGermanText("Getränke (Drinks):\ndas Getränk — drink\ndas Wasser — water\nder Kaffee — coffee\nder Tee — tea\ndie Milch — milk\nder Saft — juice\ndie Cola — cola\ndie Limonade — lemonade/soft drink\ndie heiße Schokolade — hot chocolate\n\nUseful Sentences:\nIch trinke Wasser. — I drink water.\nIch trinke gern Kaffee. — I like drinking coffee.\nIch trinke Tee zum Frühstück. — I drink tea for breakfast.\nMöchtest du einen Kaffee? — Would you like a coffee?\nIch möchte einen Saft. — I would like a juice.");
        s2_6_1_2.setEnglishTranslation("Drinks:\ndas Getränk (drink), das Wasser (water), der Kaffee (coffee), der Tee (tea), die Milch (milk), der Saft (juice), die Cola (cola), die Limonade (lemonade), die heiße Schokolade (hot chocolate)\n\nSentences:\nIch trinke Wasser. (I drink water.)\nIch trinke gern Kaffee. (I like drinking coffee.)\nIch trinke Tee zum Frühstück. (I drink tea for breakfast.)\nMöchtest du einen Kaffee? (Would you like a coffee?)\nIch möchte einen Saft. (I would like a juice.)");
        s2_6_1_2.setExplanation("Key beverage words and conversational expressions for ordering and stating drink preferences.");
        s2_6_1_2.setVocabulary("das Wasser (water) | der Kaffee (coffee) | der Tee (tea) | die Milch (milk) | der Saft (juice) | gern (gladly / like to) | möchten (would like)");
        s2_6_1_2.setGrammarRule("Using 'gern' after a verb expresses that you enjoy doing it: 'Ich trinke gern Kaffee' = 'I like drinking coffee'.");
        s2_6_1_2.setExamples("Möchtest du einen Kaffee? (Would you like a coffee?)\nIch möchte einen Saft, bitte. (I would like a juice, please.)");
        s2_6_1_2.setImportantNote("'einen Kaffee' and 'einen Saft' take the masculine accusative ending '-en'.");
        lesson2_6_1.addSlide(s2_6_1_2);
        topic2_6.addLesson(lesson2_6_1);

        // Lesson 2.6.2: Mahlzeiten & Tägliche Essensroutine
        Lesson lesson2_6_2 = new Lesson(
                "Mahlzeiten & Tägliche Essensroutine (Meals & Daily Routine)",
                "Mahlzeiten & Essensroutine",
                "Vokabular für Mahlzeiten und die Beschreibung des täglichen Essensablaufs.",
                2,
                10,
                topic2_6
        );

        Slide s2_6_2_1 = new Slide("Die Mahlzeiten (Meals & Verbs)", 1, "VOCABULARY", lesson2_6_2);
        s2_6_2_1.setGermanText("das Frühstück — breakfast\ndas Mittagessen — lunch\ndas Abendessen — dinner\ndie Mahlzeit — meal\nfrühstücken — to have breakfast\nzu Mittag essen — to have lunch\nzu Abend essen — to have dinner\nessen — to eat\nkochen — to cook\n\nUseful Sentences:\nIch frühstücke um 8 Uhr. — I have breakfast at 8 o'clock.\nIch esse um 13 Uhr zu Mittag. — I have lunch at 1 p.m.\nIch esse um 19 Uhr zu Abend. — I have dinner at 7 p.m.\nIch esse gern Pizza. — I like eating pizza.\nIch esse gern Gemüse. — I like eating vegetables.\nMeine Familie isst zusammen. — My family eats together.\nWir frühstücken zusammen. — We have breakfast together.");
        s2_6_2_1.setEnglishTranslation("das Frühstück (breakfast), das Mittagessen (lunch), das Abendessen (dinner), die Mahlzeit (meal), frühstücken (to have breakfast), zu Mittag essen (to have lunch), zu Abend essen (to have dinner), essen (to eat), kochen (to cook)\n\nSentences:\nIch frühstücke um 8 Uhr. (I have breakfast at 8 o'clock.)\nIch esse um 13 Uhr zu Mittag. (I have lunch at 1 p.m.)\nIch esse um 19 Uhr zu Abend. (I have dinner at 7 p.m.)\nIch esse gern Pizza. (I like eating pizza.)\nMeine Familie isst zusammen. (My family eats together.)\nWir frühstücken zusammen. (We have breakfast together.)");
        s2_6_2_1.setExplanation("German meal names are neuter (das Frühstück, das Mittagessen, das Abendessen). You can express having meals using either verbs (frühstücken) or phrases (zu Mittag essen, zu Abend essen).");
        s2_6_2_1.setVocabulary("das Frühstück (breakfast) | das Mittagessen (lunch) | das Abendessen (dinner) | frühstücken (to have breakfast) | essen (to eat) | kochen (to cook) | zusammen (together)");
        s2_6_2_1.setGrammarRule("Conjugation of 'essen' (stem changes e -> i):\n• ich esse\n• du isst\n• er/sie/es isst (Meine Familie isst zusammen)\n• wir essen\n• ihr esst\n• sie/Sie essen");
        s2_6_2_1.setExamples("Wir frühstücken zusammen um 8 Uhr.\nMeine Familie isst jeden Abend zusammen.");
        s2_6_2_1.setImportantNote("Notice 'Meine Familie' is singular, so it uses 'isst': 'Meine Familie isst zusammen'.");
        lesson2_6_2.addSlide(s2_6_2_1);

        Slide s2_6_2_2 = new Slide("Tägliche Essensroutine (Daily Meal Routine Schedule)", 2, "DIALOGUE", lesson2_6_2);
        s2_6_2_2.setGermanText("Tägliche Essensroutine:\n\nIch stehe um 7 Uhr auf.\nIch frühstücke um 8 Uhr.\nZum Frühstück esse ich Brot.\nIch trinke Kaffee.\nIch esse um 13 Uhr zu Mittag.\nZum Mittagessen esse ich Reis und Gemüse.\nAm Abend esse ich mit meiner Familie.\nWir essen um 19 Uhr zu Abend.\nNach dem Abendessen trinke ich Tee.");
        s2_6_2_2.setEnglishTranslation("Daily Meal Routine:\n\nI get up at 7 o'clock.\nI have breakfast at 8 o'clock.\nFor breakfast, I eat bread.\nI drink coffee.\nI have lunch at 1 p.m.\nFor lunch, I eat rice and vegetables.\nIn the evening, I eat with my family.\nWe have dinner at 7 p.m.\nAfter dinner, I drink tea.");
        s2_6_2_2.setExplanation("A complete chronological daily schedule demonstrating time expressions, meal prepositions, and separable verbs.");
        s2_6_2_2.setVocabulary("aufstehen (to get up) | zum Frühstück (for breakfast) | zum Mittagessen (for lunch) | am Abend (in the evening) | nach dem Abendessen (after dinner) | mit meiner Familie (with my family)");
        s2_6_2_2.setGrammarRule("Preposition Structures for Meals & Times:\n• um + [Zeit]: um 7 Uhr, um 8 Uhr, um 13 Uhr, um 19 Uhr\n• zum + [Mahlzeit]: zum Frühstück, zum Mittagessen\n• nach + [Dativ]: nach dem Abendessen");
        s2_6_2_2.setExamples("Zum Frühstück esse ich Brot und trinke Kaffee.\nZum Mittagessen esse ich Reis und Gemüse.\nNach dem Abendessen trinke ich Tee.");
        s2_6_2_2.setImportantNote("Notice how 'Zum Frühstück' and 'Nach dem Abendessen' put the verb in Position 2: 'Zum Frühstück [1] esse [2] ich [3] Brot [4]'.");
        lesson2_6_2.addSlide(s2_6_2_2);
        topic2_6.addLesson(lesson2_6_2);
        mod2.addTopic(topic2_6);

        // =========================================================================
        // TOPIC 7: Family, Accusative Case & Negation (Familie, Akkusativ & Verneinung)
        // =========================================================================
        Topic topic2_7 = new Topic(
                "Family, Accusative Case & Negation (Familie, Akkusativ & Verneinung)",
                "Familie, Akkusativ & Verneinung",
                "Familienmitglieder, der Akkusativ als direktes Objekt, Akkusativ-Verneinung und wichtige Fragewörter.",
                7,
                mod2
        );

        // Lesson 2.7.1: Die Familie
        Lesson lesson2_7_1 = new Lesson(
                "Die Familie (Family Members)",
                "Die Familie",
                "Vokabular für Verwandte und Familienbeschreibungen.",
                1,
                10,
                topic2_7
        );

        Slide s2_7_1_1 = new Slide("Familienmitglieder (Family Members)", 1, "VOCABULARY", lesson2_7_1);
        s2_7_1_1.setGermanText("die Familie — family\ndie Eltern — parents\nder Vater — father\ndie Mutter — mother\nder Sohn — son\ndie Tochter — daughter\nder Bruder — brother\ndie Schwester — sister\nder Großvater — grandfather\ndie Großmutter — grandmother\nder Onkel — uncle\ndie Tante — aunt\nder Cousin — male cousin\ndie Cousine — female cousin\ndas Kind — child\ndie Kinder — children");
        s2_7_1_1.setEnglishTranslation("the family\nthe parents\nthe father\nthe mother\nthe son\nthe daughter\nthe brother\nthe sister\nthe grandfather\nthe grandmother\nthe uncle\nthe aunt\nthe male cousin\nthe female cousin\nthe child\nthe children");
        s2_7_1_1.setExplanation("All primary family members and generational terms in German.");
        s2_7_1_1.setVocabulary("die Familie (family) | die Eltern (parents) | der Vater (father) | die Mutter (mother) | der Sohn (son) | die Tochter (daughter) | der Bruder (brother) | die Schwester (sister) | der Großvater (grandfather) | die Großmutter (grandmother) | der Onkel (uncle) | die Tante (aunt) | der Cousin (cousin m) | die Cousine (cousin f)");
        s2_7_1_1.setGrammarRule("Gender alignment:\n• Male relatives: der Vater, der Sohn, der Bruder, der Großvater, der Onkel, der Cousin\n• Female relatives: die Mutter, die Tochter, die Schwester, die Großmutter, die Tante, die Cousine\n• Plural only: die Eltern (the parents)");
        s2_7_1_1.setExamples("Das ist mein Vater und das ist meine Mutter. (This is my father and this is my mother.)\nIch habe einen Bruder und zwei Schwestern. (I have a brother and two sisters.)");
        s2_7_1_1.setImportantNote("'die Eltern' is always plural in German.");
        lesson2_7_1.addSlide(s2_7_1_1);

        Slide s2_7_1_2 = new Slide("Sätze über die Familie (Sentences About Family)", 2, "EXAMPLE", lesson2_7_1);
        s2_7_1_2.setGermanText("Das ist meine Familie. — This is my family.\nDas ist mein Vater. — This is my father.\nDas ist meine Mutter. — This is my mother.\nIch habe einen Bruder. — I have a brother.\nIch habe eine Schwester. — I have a sister.\nMeine Eltern wohnen in Pune. — My parents live in Pune.\nWir essen zusammen. — We eat together.\nMeine Familie isst jeden Abend zusammen. — My family eats together every evening.");
        s2_7_1_2.setEnglishTranslation("Das ist meine Familie. (This is my family.)\nDas ist mein Vater. (This is my father.)\nDas ist meine Mutter. (This is my mother.)\nIch habe einen Bruder. (I have a brother.)\nIch habe eine Schwester. (I have a sister.)\nMeine Eltern wohnen in Pune. (My parents live in Pune.)\nWir essen zusammen. (We eat together.)\nMeine Familie isst jeden Abend zusammen. (My family eats together every evening.)");
        s2_7_1_2.setExplanation("Describing family members, where they live, and shared activities.");
        s2_7_1_2.setVocabulary("mein / meine (my) | haben (to have) | wohnen in (to live in) | jeden Abend (every evening) | zusammen (together)");
        s2_7_1_2.setGrammarRule("Possessive pronoun 'mein':\n• Masculine (Nom): mein Vater\n• Feminine (Nom): meine Mutter / meine Familie\n• Masculine (Acc): einen / meinen Bruder\n• Feminine (Acc): eine / meine Schwester");
        s2_7_1_2.setExamples("Das ist mein Vater. (This is my father.)\nIch habe einen Bruder und eine Schwester. (I have a brother and a sister.)\nMeine Eltern wohnen in Pune. (My parents live in Pune.)");
        s2_7_1_2.setImportantNote("Notice 'Ich habe einen Bruder' uses the masculine accusative ending '-en' on 'einen'.");
        lesson2_7_1.addSlide(s2_7_1_2);
        topic2_7.addLesson(lesson2_7_1);

        // Lesson 2.7.2: Der Akkusativ
        Lesson lesson2_7_2 = new Lesson(
                "Der Akkusativ (The Accusative Case)",
                "Der Akkusativ",
                "Das direkte Objekt im Deutschen und die Artikelveränderungen im Akkusativ.",
                2,
                12,
                topic2_7
        );

        Slide s2_7_2_1 = new Slide("Akkusativ Konzept & Artikeltabellen (Accusative Concept & Tables)", 1, "GRAMMAR", lesson2_7_2);
        s2_7_2_1.setGermanText("The accusative is commonly used for the direct object of a sentence.\n\nExample:\nIch sehe den Mann. -> I see the man.\nHere:\n• Ich = subject (Nominative)\n• sehe = verb\n• den Mann = direct object -> Accusative\n\nAccusative Definite Articles:\nGender | Nominative | Accusative\nMasculine | der | den\nFeminine | die | die\nNeuter | das | das\nPlural | die | die\n\nAccusative Indefinite Articles:\nGender | Nominative | Accusative\nMasculine | ein | einen\nFeminine | eine | eine\nNeuter | ein | ein\n\nKey pattern to remember:\nder -> den\nein -> einen\n\nThe other basic article forms remain unchanged.");
        s2_7_2_1.setEnglishTranslation("Direct object rule:\nIch sehe den Mann. (I see the man.)\n• Ich = subject\n• sehe = verb\n• den Mann = direct object (Accusative)\n\nDefinite Articles:\n• Masculine: der -> den\n• Feminine: die -> die (unchanged)\n• Neuter: das -> das (unchanged)\n• Plural: die -> die (unchanged)\n\nIndefinite Articles:\n• Masculine: ein -> einen\n• Feminine: eine -> eine (unchanged)\n• Neuter: ein -> ein (unchanged)\n\nKey pattern:\nder -> den\nein -> einen");
        s2_7_2_1.setExplanation("The accusative case identifies the direct recipient of an action. In German, ONLY masculine articles change in the accusative (der -> den, ein -> einen, mein -> meinen, kein -> keinen). Feminine, neuter, and plural articles remain identical to the nominative.");
        s2_7_2_1.setVocabulary("der Akkusativ (accusative) | das direkte Objekt (direct object) | der Fall (grammatical case)");
        s2_7_2_1.setGrammarRule("Key Accusative Change:\nONLY masculine changes in the Accusative:\n• der -> den\n• ein -> einen\n• mein -> meinen\n• kein -> keinen\nFeminine (die/eine), Neuter (das/ein), and Plural (die) do NOT change!");
        s2_7_2_1.setExamples("Ich sehe den Mann. (I see the man.)\nIch kaufe einen Apfel. (I buy an apple.)\nIch nehme den Bus. (I take the bus.)");
        s2_7_2_1.setImportantNote("Whenever you have a masculine direct object, remember: der -> den and ein -> einen!");
        lesson2_7_2.addSlide(s2_7_2_1);

        Slide s2_7_2_2 = new Slide("Akkusativ in der Praxis (Accusative in Context: City, Food & Family)", 2, "EXAMPLE", lesson2_7_2);
        s2_7_2_2.setGermanText("City Life:\nIch sehe den Bahnhof. — I see the railway station.\nIch sehe die Schule. — I see the school.\nIch sehe das Gebäude. — I see the building.\nIch sehe die Häuser. — I see the houses.\nIch nehme den Bus. — I take the bus.\nIch nehme einen Bus. — I take a bus.\nIch kaufe ein Ticket. — I buy a ticket.\nIch besuche die Stadt. — I visit the city.\n\nFood:\nIch esse einen Apfel. — I eat an apple.\nIch esse eine Banane. — I eat a banana.\nIch esse das Brot. — I eat the bread.\nIch kaufe einen Kuchen. — I buy a cake.\nIch kaufe eine Pizza. — I buy a pizza.\nIch trinke den Kaffee. — I drink the coffee.\nIch trinke einen Kaffee. — I drink a coffee.\nIch esse das Gemüse. — I eat the vegetables.\n\nFamily:\nIch sehe meinen Vater. — I see my father.\nIch sehe meine Mutter. — I see my mother.\nIch besuche meinen Bruder. — I visit my brother.\nIch besuche meine Schwester. — I visit my sister.\nIch habe einen Bruder. — I have a brother.\nIch habe eine Schwester. — I have a sister.\nIch sehe das Kind. — I see the child.");
        s2_7_2_2.setEnglishTranslation("City Life:\nIch sehe den Bahnhof. (I see the railway station.)\nIch sehe die Schule. (I see the school.)\nIch nehme den Bus / einen Bus. (I take the bus / a bus.)\nIch kaufe ein Ticket. (I buy a ticket.)\nIch besuche die Stadt. (I visit the city.)\n\nFood:\nIch esse einen Apfel. (I eat an apple.)\nIch esse eine Banane. (I eat a banana.)\nIch kaufe einen Kuchen / eine Pizza. (I buy a cake / a pizza.)\nIch trinke den Kaffee / einen Kaffee. (I drink the coffee / a coffee.)\n\nFamily:\nIch sehe meinen Vater / meine Mutter. (I see my father / my mother.)\nIch besuche meinen Bruder / meine Schwester. (I visit my brother / my sister.)\nIch habe einen Bruder. (I have a brother.)\nIch sehe das Kind. (I see the child.)");
        s2_7_2_2.setExplanation("Observe how verbs like 'sehen' (see), 'nehmen' (take), 'kaufen' (buy), 'besuchen' (visit), 'essen' (eat), 'trinken' (drink), and 'haben' (have) all trigger the accusative case for their direct objects.");
        s2_7_2_2.setVocabulary("sehen (to see) | nehmen (to take) | kaufen (to buy) | besuchen (to visit) | das Ticket (ticket)");
        s2_7_2_2.setGrammarRule("Verbs triggering the Accusative:\n• sehen, nehmen, kaufen, besuchen, essen, trinken, haben");
        s2_7_2_2.setExamples("Ich nehme den Bus. (Masc acc: den Bus)\nIch kaufe eine Pizza. (Fem acc: eine Pizza)\nIch sehe das Kind. (Neut acc: das Kind)");
        s2_7_2_2.setImportantNote("Notice 'meinen Vater' and 'meinen Bruder' change to '-en' because father and brother are masculine nouns in the accusative.");
        lesson2_7_2.addSlide(s2_7_2_2);
        topic2_7.addLesson(lesson2_7_2);

        // Lesson 2.7.3: Akkusativ-Verneinung & Fragewörter
        Lesson lesson2_7_3 = new Lesson(
                "Akkusativ-Verneinung & Fragewörter (Accusative Negation & Question Words)",
                "Akkusativ-Verneinung & Fragewörter",
                "Verneinung im Akkusativ und wichtige W-Fragewörter für dieses Modul.",
                3,
                10,
                topic2_7
        );

        Slide s2_7_3_1 = new Slide("Akkusativ + Verneinung (Accusative + Negation)", 1, "GRAMMAR", lesson2_7_3);
        s2_7_3_1.setGermanText("This is especially important because kein changes in the accusative.\n\nPositive | Negative | English\nIch habe einen Hund. | Ich habe keinen Hund. | I have a dog. / I don't have a dog.\nIch kaufe einen Apfel. | Ich kaufe keinen Apfel. | I buy an apple. / I don't buy an apple.\nIch habe eine Schwester. | Ich habe keine Schwester. | I have a sister. / I don't have a sister.\nIch kaufe ein Ticket. | Ich kaufe kein Ticket. | I buy a ticket. / I don't buy a ticket.\nIch sehe den Bus. | Ich sehe den Bus nicht. | I see the bus. / I don't see the bus.");
        s2_7_3_1.setEnglishTranslation("Positive -> Negative:\n• Ich habe einen Hund. -> Ich habe keinen Hund. (I have a dog. / I don't have a dog.)\n• Ich kaufe einen Apfel. -> Ich kaufe keinen Apfel. (I buy an apple. / I don't buy an apple.)\n• Ich habe eine Schwester. -> Ich habe keine Schwester. (I have a sister. / I don't have a sister.)\n• Ich kaufe ein Ticket. -> Ich kaufe kein Ticket. (I buy a ticket. / I don't buy a ticket.)\n• Ich sehe den Bus. -> Ich sehe den Bus nicht. (I see the bus. / I don't see the bus.)");
        s2_7_3_1.setExplanation("When negating an indefinite noun in the accusative, 'kein' follows the same ending as 'ein':\n• einen -> keinen (masculine)\n• eine -> keine (feminine)\n• ein -> kein (neuter)\nWhen negating a definite noun (den Bus), use 'nicht' at the end of the sentence.");
        s2_7_3_1.setVocabulary("keinen Hund (no dog) | keinen Apfel (no apple) | keine Schwester (no sister) | kein Ticket (no ticket)");
        s2_7_3_1.setGrammarRule("Accusative Negation Patterns:\n• Masc: einen -> keinen Hund / keinen Apfel\n• Fem: eine -> keine Schwester\n• Neut: ein -> kein Ticket\n• Definite noun: den Bus -> den Bus nicht");
        s2_7_3_1.setExamples("Ich habe keinen Hund. (I don't have a dog.)\nIch kaufe keinen Apfel. (I don't buy an apple.)\nIch sehe den Bus nicht. (I don't see the bus.)");
        s2_7_3_1.setImportantNote("Notice the distinction: 'keinen Apfel' (indefinite noun) vs. 'den Bus nicht' (definite noun with 'den').");
        lesson2_7_3.addSlide(s2_7_3_1);

        Slide s2_7_3_2 = new Slide("Wichtige Fragewörter (Important Question Words)", 2, "TABLE", lesson2_7_3);
        s2_7_3_2.setGermanText("Important Question Words:\n\nGerman | English\nWer? | Who?\nWas? | What?\nWo? | Where?\nWohin? | Where to?\nWoher? | Where from?\nWann? | When?\nWie? | How?\nWarum? | Why?\nWie viel? | How much?\nWie viele? | How many?\nWelcher? | Which? — masculine\nWelche? | Which? — feminine/plural\nWelches? | Which? — neuter");
        s2_7_3_2.setEnglishTranslation("Wer? (Who?) | Was? (What?) | Wo? (Where?) | Wohin? (Where to?) | Woher? (Where from?) | Wann? (When?) | Wie? (How?) | Warum? (Why?) | Wie viel? (How much?) | Wie viele? (How many?) | Welcher? (Which? - masc) | Welche? (Which? - fem/plur) | Welches? (Which? - neut)");
        s2_7_3_2.setExplanation("Comprehensive reference of all interrogative question words used across Module II.");
        s2_7_3_2.setVocabulary("Wohin? (Where to?) | Wie viel? (How much?) | Wie viele? (How many?) | Welcher? (Which? masc) | Welche? (Which? fem/plur) | Welches? (Which? neut)");
        s2_7_3_2.setGrammarRule("Direction vs Location:\n• Wo? -> static location (Wo ist der Bahnhof?)\n• Wohin? -> direction/destination (Wohin fährst du?)\n• Woher? -> origin (Woher kommst du?)\nQuantity:\n• Wie viel? -> uncountable (Wie viel kostet das?)\n• Wie viele? -> countable (Wie viele Geschwister hast du?)");
        s2_7_3_2.setExamples("Wohin fährst du mit dem Zug? (Where are you travelling to by train?)\nWie viel kostet das Ticket? (How much does the ticket cost?)\nWelcher Bus fährt zum Zentrum? (Which bus goes to the city centre?)");
        s2_7_3_2.setImportantNote("'Welcher' declines like definite articles: Welcher (der), Welche (die), Welches (das).");
        lesson2_7_3.addSlide(s2_7_3_2);
        topic2_7.addLesson(lesson2_7_3);
        mod2.addTopic(topic2_7);

        moduleRepository.save(mod2);
    }

    private void initModule3() {
        Optional<Module> existingOpt = moduleRepository.findByCode("MODULE_3");
        Module mod3;
        if (existingOpt.isPresent()) {
            mod3 = existingOpt.get();
            if (mod3.getTopics().size() == 7) {
                return;
            }
            userProgressRepository.deleteAll();
            mod3.getTopics().clear();
        } else {
            mod3 = new Module();
            mod3.setCode("MODULE_3");
        }

        mod3.setTitle("Module III: Time, Prepositions, Modal Verbs, Professions, Health & Perfect Tense");
        mod3.setGermanTitle("Modul III: Zeit, Präpositionen, Modalverben, Berufe, Gesundheit & Perfekt");
        mod3.setDescription("Ein vollständiges Wortschatz- und Grammatikverzeichnis für dieses Modul, mit englischer Übersetzung für jedes Element. Master clock time, appointment scheduling, time prepositions (am, um, von... bis, in, ab, seit, gegen), modal verbs (müssen, sollen, können, dürfen), professions and workplaces, health, body parts and doctor visits, the German Perfect Tense (haben/sein + Partizip II), and interactive classroom activities.");
        mod3.setLevel("A1.2");
        mod3.setOrderIndex(3);
        mod3.setActive(true);

        // ==========================================
        // TOPIC 3.1: Uhrzeit & Termine (Telling Time & Appointments)
        // ==========================================
        Topic topic3_1 = new Topic(
                "Uhrzeit & Termine (Telling Time & Scheduling Appointments)",
                "Uhrzeit & Termine",
                "Kernvokabular, Fragen und Beispielsätze rund um Uhrzeit und Terminvereinbarung.",
                1,
                mod3
        );

        // Lesson 3.1.1: Wortschatz: Uhrzeit & Termine
        Lesson lesson3_1_1 = new Lesson(
                "Wortschatz: Uhrzeit & Termine (Vocabulary: Time & Appointments)",
                "Wortschatz: Uhrzeit & Termine",
                "Wichtige Nomen, Verben und Redewendungen zur Zeit- und Terminplanung.",
                1,
                8,
                topic3_1
        );

        Slide s3_1_1_1 = new Slide("Kernvokabular zu Terminen (Core Appointment Vocabulary)", 1, "VOCABULARY", lesson3_1_1);
        s3_1_1_1.setGermanText("der Termin — the appointment\ndie Uhrzeit — the time (of day)\ndie Verabredung — the arrangement / date\nder Kalender — the calendar\ndie Sprechstunde — the office hours / consultation hours\nvereinbaren — to arrange\npünktlich — on time / punctual\nsich verspäten — to be late\nabsagen — to cancel\nverschieben — to postpone / reschedule\nfrei haben — to be free / available\ndie Verabredung verpassen — to miss the appointment");
        s3_1_1_1.setEnglishTranslation("the appointment\nthe time (of day)\nthe arrangement / date\nthe calendar\nthe office hours / consultation hours\nto arrange\non time / punctual\nto be late\nto cancel\nto postpone / reschedule\nto be free / available\nto miss the appointment");
        s3_1_1_1.setExplanation("Scheduling appointments (Termine vereinbaren) and observing punctuality (Pünktlichkeit) are core aspects of daily life and work in German-speaking countries.");
        s3_1_1_1.setVocabulary("der Termin (the appointment) | die Uhrzeit (the time) | die Verabredung (the date/arrangement) | der Kalender (the calendar) | vereinbaren (to arrange) | pünktlich (punctual) | sich verspäten (to be late) | absagen (to cancel) | verschieben (to reschedule) | die Sprechstunde (consultation hours) | frei haben (to be free) | verpassen (to miss)");
        s3_1_1_1.setGrammarRule("Verb classifications:\n• Separable verbs: absagen (ich sage ab), verschieben (ich verschiebe), frei haben (ich habe frei)\n• Reflexive verb: sich verspäten (ich verspäte mich)\n• Regular verbs: vereinbaren (ich vereinbare), verpassen (ich verpasse)");
        s3_1_1_1.setExamples("Ich möchte einen Termin vereinbaren. (I would like to arrange an appointment.)\nIch muss den Termin leider absagen. (Unfortunately I have to cancel the appointment.)\nEntschuldigung, ich habe mich verspätet. (Excuse me, I am running late.)");
        s3_1_1_1.setImportantNote("Notice genders: der Termin (m), die Uhrzeit (f), die Verabredung (f), der Kalender (m), die Sprechstunde (f).");
        lesson3_1_1.addSlide(s3_1_1_1);

        Slide s3_1_1_2 = new Slide("Fragen & Antworten zur Uhrzeit (Telling Time & Q&A)", 2, "DIALOGUE", lesson3_1_1);
        s3_1_1_2.setGermanText("Wie spät ist es? — What time is it?\nWie viel Uhr ist es? — What time is it?\nEs ist neun Uhr fünfzehn. — It is nine fifteen.\nEs ist Viertel nach neun. — It is a quarter past nine.\nEs ist halb zehn. — It is half past nine (9:30).\nEs ist zehn vor zehn. — It is ten to ten (9:50).");
        s3_1_1_2.setEnglishTranslation("What time is it?\nWhat time is it?\nIt is nine fifteen.\nIt is a quarter past nine.\nIt is half past nine (9:30).\nIt is ten to ten (9:50).");
        s3_1_1_2.setExplanation("In German, both 'Wie spät ist es?' (How late is it?) and 'Wie viel Uhr ist es?' (How much clock is it?) are standard ways to ask for the time. Time can be given formally (neun Uhr fünfzehn) or informally (Viertel nach neun, halb zehn, zehn vor zehn).");
        s3_1_1_2.setVocabulary("wie spät (how late / what time) | wie viel Uhr (what time) | nach (past) | vor (to/before) | halb (half) | das Viertel (quarter)");
        s3_1_1_2.setGrammarRule("Informal Time Telling:\n• nach = past the hour (Viertel nach neun = 9:15)\n• halb = half an hour to the NEXT hour (halb zehn = 9:30, NOT 10:30!)\n• vor = minutes before the next hour (zehn vor zehn = 9:50)");
        s3_1_1_2.setExamples("A: Entschuldigung, wie viel Uhr ist es?\nB: Es ist jetzt genau Viertel nach neun.\nA: Wann kommt der Bus?\nB: Um zehn vor zehn.");
        s3_1_1_2.setImportantNote("CAUTION: 'halb zehn' means 9:30 (halfway toward ten), not 10:30!");
        lesson3_1_1.addSlide(s3_1_1_2);
        topic3_1.addLesson(lesson3_1_1);

        // Lesson 3.1.2: Termine vereinbaren & verschieben
        Lesson lesson3_1_2 = new Lesson(
                "Termine vereinbaren & verschieben (Making & Rescheduling Appointments)",
                "Termine vereinbaren & verschieben",
                "Praktische Dialoge und Redemittel, um Termine zu vereinbaren, zu bestätigen oder zu verschieben.",
                2,
                10,
                topic3_1
        );

        Slide s3_1_2_1 = new Slide("Terminvereinbarung im Alltag (Arranging an Appointment)", 1, "DIALOGUE", lesson3_1_2);
        s3_1_2_1.setGermanText("Wann fängt der Termin an?\nDer Termin fängt um 14 Uhr an.\n\nHaben Sie am Montag Zeit?\nJa, von 10 bis 12 Uhr bin ich frei.\n\nIch hätte gern einen Termin am …\nPasst Ihnen … Uhr?\nIch habe von … bis … Zeit.");
        s3_1_2_1.setEnglishTranslation("When does the appointment start?\nThe appointment starts at 2 p.m.\n\nDo you have time on Monday?\nYes, I'm free from 10 to 12.\n\nI would like an appointment on …\nDoes … o'clock suit you?\nI have time from … to …");
        s3_1_2_1.setExplanation("Essential phrases when speaking with a doctor's receptionist, secretary, client, or friend to agree on a meeting time.");
        s3_1_2_1.setVocabulary("anfangen (to start) | Zeit haben (to have time) | frei sein (to be free) | Ich hätte gern (I would like to have) | passen (to suit / fit)");
        s3_1_2_1.setGrammarRule("Useful Patterns:\n• 'anfangen' separates: 'Der Termin fängt um 14 Uhr an.'\n• 'Ich hätte gern...' is the polite subjunctive form.\n• 'Passt Ihnen [Zeit]?' takes the Dative pronoun (Ihnen formal, dir informal).");
        s3_1_2_1.setExamples("A: Guten Tag, ich hätte gern einen Termin am Dienstag.\nB: Haben Sie um 11 Uhr Zeit?\nA: Ja, von 10 bis 12 Uhr bin ich frei. 11 Uhr passt mir gut!");
        s3_1_2_1.setImportantNote("Use 'Passt Ihnen ...?' in formal situations and 'Passt dir ...?' with friends and peers.");
        lesson3_1_2.addSlide(s3_1_2_1);

        Slide s3_1_2_2 = new Slide("Termine ändern & verschieben (Rescheduling & Cancelling)", 2, "DIALOGUE", lesson3_1_2);
        s3_1_2_2.setGermanText("Können wir den Termin verschieben?\nEs tut mir leid, ich muss den Termin absagen.\nIch habe mich leider verspätet.\nIch habe die Verabredung verpasst.");
        s3_1_2_2.setEnglishTranslation("Can we reschedule the appointment?\nI am sorry, I have to cancel the appointment.\nUnfortunately I am running late.\nI missed the appointment.");
        s3_1_2_2.setExplanation("When schedules change, polite communication is crucial. Use these formulas to reschedule (verschieben) or cancel (absagen).");
        s3_1_2_2.setVocabulary("verschieben (to postpone/reschedule) | absagen (to cancel) | sich verspäten (to be late) | verpassen (to miss) | es tut mir leid (I am sorry)");
        s3_1_2_2.setGrammarRule("Modal verb + Infinitive at sentence end: 'Können wir den Termin verschieben?' (verschieben stays in infinitive at the end).");
        s3_1_2_2.setExamples("A: Guten Tag, können wir unseren Termin auf Donnerstag verschieben?\nB: Ja, Donnerstag um 15 Uhr ist frei.\nA: Vielen Dank für Ihr Verständnis!");
        s3_1_2_2.setImportantNote("Remember: 'verschieben' means to postpone or change the time, while 'absagen' means to cancel completely.");
        lesson3_1_2.addSlide(s3_1_2_2);
        topic3_1.addLesson(lesson3_1_2);
        mod3.addTopic(topic3_1);

        // ==========================================
        // TOPIC 3.2: Präpositionen der Zeit (Time Prepositions)
        // ==========================================
        Topic topic3_2 = new Topic(
                "Präpositionen der Zeit (Time Prepositions: am, um, von … bis, im, ab, seit, gegen)",
                "Präpositionen der Zeit",
                "Zeitpräpositionen mit ihrer Funktion, Beispielen und Verschmelzungen.",
                2,
                mod3
        );

        // Lesson 3.2.1: Zeitpräpositionen Übersicht
        Lesson lesson3_2_1 = new Lesson(
                "Die wichtigsten Zeitpräpositionen (Key Time Prepositions)",
                "Die wichtigsten Zeitpräpositionen",
                "Übersicht aller wichtigen Präpositionen für Zeitangaben aus dem Modul.",
                1,
                10,
                topic3_2
        );

        Slide s3_2_1_1 = new Slide("Zeitpräpositionen & ihre Bedeutung (Time Prepositions Table)", 1, "TABLE", lesson3_2_1);
        s3_2_1_1.setGermanText("Präposition | Bedeutung / Meaning | Beispiel / Example\n" +
                "am | on / at (for days & parts of day) | am Montag, am Morgen, am Wochenende\n" +
                "um | at (for exact clock time) | um 8 Uhr, um Punkt zwölf\n" +
                "von … bis | from … to (time span) | von 9 bis 17 Uhr, von Montag bis Freitag\n" +
                "in | in (for months, years, seasons) | im Januar, im Sommer, im Jahr 2026\n" +
                "ab | from … (onwards) | ab morgen, ab nächster Woche\n" +
                "seit | since / for (ongoing duration) | seit gestern, seit zwei Wochen\n" +
                "gegen | around / toward (approx. time) | gegen Mittag, gegen 15 Uhr");
        s3_2_1_1.setEnglishTranslation("am: on / at (for days & parts of day) -> on Monday, in the morning, at the weekend\num: at (for exact clock time) -> at 8 o'clock, at twelve sharp\nvon … bis: from … to (time span) -> from 9 to 17, from Monday to Friday\nin: in (for months, years, seasons) -> in January, in summer, in the year 2026\nab: from … (onwards) -> from tomorrow, from next week\nseit: since / for (ongoing duration) -> since yesterday, for two weeks\ngegen: around / toward (approx. time) -> around noon, around 15 o'clock");
        s3_2_1_1.setExplanation("Each German time preposition matches a specific category of temporal reference: days (am), exact hours (um), intervals (von... bis), months/seasons (im), future starting points (ab), ongoing past durations (seit), and approximate times (gegen).");
        s3_2_1_1.setVocabulary("am Montag (on Monday) | am Morgen (in the morning) | am Wochenende (at the weekend) | um Punkt zwölf (at twelve sharp) | im Januar (in January) | im Sommer (in summer) | ab morgen (from tomorrow) | seit gestern (since yesterday) | gegen Mittag (around noon)");
        s3_2_1_1.setGrammarRule("Temporal Prepositions Guide:\n• am -> Days & Daytime (am Montag, am Abend)\n• um -> Clock time (um 8 Uhr, um 14:30 Uhr)\n• von... bis -> Time intervals (von 10 bis 12 Uhr)\n• im -> Months & Seasons (im Juli, im Winter)\n• ab -> Starting from a future point (ab morgen)\n• seit -> Ongoing past actions (seit zwei Wochen)\n• gegen -> Approximate time (gegen 15 Uhr)");
        s3_2_1_1.setExamples("Ich arbeite von 9 bis 17 Uhr. (I work from 9 to 17.)\nDer Termin ist am Montag um 8 Uhr. (The appointment is on Monday at 8.)\nWir fliegen im Sommer nach Deutschland. (We are flying to Germany in the summer.)");
        s3_2_1_1.setImportantNote("Always use 'um' for exact clock times and 'am' for days and parts of the day (except 'in der Nacht').");
        lesson3_2_1.addSlide(s3_2_1_1);

        Slide s3_2_1_2 = new Slide("Verschmelzungen & Zeitregeln (Contractions & Rules for Time Prepositions)", 2, "GRAMMAR", lesson3_2_1);
        s3_2_1_2.setGermanText("Merksatz: „am“ und „in dem“ verschmelzen zu „im“; „an + dem“ verschmilzt zu „am“.\nDiese Wörter machen Zeitangaben im Satz präzise.\n\nan + dem = am (am Montag, am Morgen, am Abend, am Wochenende)\nin + dem = im (im Januar, im Februar, im Sommer, im Herbst, im Winter)");
        s3_2_1_2.setEnglishTranslation("Rule to remember: “am” (an + dem) and “im” (in + dem) are contractions. These prepositions make time references precise in a sentence.");
        s3_2_1_2.setExplanation("German prepositions frequently fuse with the masculine/neuter dative definite article 'dem' to create compact contractions like 'am' and 'im'.");
        s3_2_1_2.setVocabulary("die Verschmelzung (contraction) | der Merksatz (rule to remember) | präzise (precise) | die Zeitangabe (time reference)");
        s3_2_1_2.setGrammarRule("Contractions Formulas:\n• an + dem -> am (am Montag, am Vormittag, am Wochenende)\n• in + dem -> im (im Januar, im Sommer, im Jahr 2026)");
        s3_2_1_2.setExamples("Wir sehen uns am Wochenende. (We will see each other at the weekend.)\nIm Januar ist es in Deutschland kalt. (In January it is cold in Germany.)\nAb nächster Woche habe ich Urlaub. (From next week onwards I am on vacation.)");
        s3_2_1_2.setImportantNote("Remember: 'am' = on/at a day; 'im' = in a month or season; 'um' = at a specific clock time.");
        lesson3_2_1.addSlide(s3_2_1_2);
        topic3_2.addLesson(lesson3_2_1);

        // Lesson 3.2.2: Zeitangaben im Satz
        Lesson lesson3_2_2 = new Lesson(
                "Zeitangaben im Satz (Time Expressions in Sentences)",
                "Zeitangaben im Satz",
                "Praktische Anwendung von Zeitpräpositionen in ganzen Sätzen.",
                2,
                8,
                topic3_2
        );

        Slide s3_2_2_1 = new Slide("Beispielsätze mit Zeitpräpositionen (Time Prepositions in Context)", 1, "EXAMPLE", lesson3_2_2);
        s3_2_2_1.setGermanText("am Montag: Ich habe am Montag einen Termin beim Arzt.\nam Wochenende: Am Wochenende bin ich frei.\num 8 Uhr: Die Schule beginnt um 8 Uhr.\num Punkt zwölf: Das Mittagessen ist um Punkt zwölf.\nvon … bis: Ich arbeite von Montag bis Freitag von 9 bis 17 Uhr.\nim Sommer: Im Sommer reisen wir nach Berlin.\nab morgen: Ab morgen lerne ich jeden Tag Deutsch.\nseit gestern: Ich bin seit gestern erkältet.\ngegen Mittag: Wir treffen uns gegen Mittag.");
        s3_2_2_1.setEnglishTranslation("on Monday: I have an appointment at the doctor's on Monday.\nat the weekend: At the weekend I am free.\nat 8 o'clock: School starts at 8 o'clock.\nat twelve sharp: Lunch is at twelve sharp.\nfrom ... to: I work from Monday to Friday from 9 to 17.\nin summer: In summer we travel to Berlin.\nfrom tomorrow: From tomorrow onwards I study German every day.\nsince yesterday: I have had a cold since yesterday.\naround noon: We meet around noon.");
        s3_2_2_1.setExplanation("Notice that when a time phrase starts the sentence (Position 1), the conjugated verb immediately follows in Position 2, followed by the subject (Inversion).");
        s3_2_2_1.setVocabulary("das Mittagessen (lunch) | reisen (to travel) | jeden Tag (every day) | der Urlaub (vacation) | beginnen (to begin)");
        s3_2_2_1.setGrammarRule("Verb-Second Rule with Time Expressions:\n• Position 1: Am Montag\n• Position 2: habe (Verb)\n• Position 3: ich (Subject)\n• Rest: einen Termin beim Arzt.");
        s3_2_2_1.setExamples("Am Montag habe ich frei. (On Monday I am free.)\nUm 8 Uhr fängt die Besprechung an. (At 8 o'clock the meeting starts.)\nSeit zwei Wochen wohne ich in München. (For two weeks I have lived in Munich.)");
        s3_2_2_1.setImportantNote("When you start a sentence with a prepositional time phrase, always place the conjugated verb directly after it!");
        lesson3_2_2.addSlide(s3_2_2_1);
        topic3_2.addLesson(lesson3_2_2);
        mod3.addTopic(topic3_2);

        // ==========================================
        // TOPIC 3.3: Modalverben (Modal Verbs)
        // ==========================================
        Topic topic3_3 = new Topic(
                "Modalverben: Notwendigkeit, Fähigkeit & Höflichkeit (Modal Verbs)",
                "Modalverben",
                "Modalverben für Notwendigkeit (müssen, sollen), Verbot/Erlaubnis (dürfen), Fähigkeit (können) und Satzstellung.",
                3,
                mod3
        );

        // Lesson 3.3.1: Notwendigkeit & Verbot
        Lesson lesson3_3_1 = new Lesson(
                "Notwendigkeit & Verbot: müssen, sollen, nicht dürfen (Necessity & Prohibition)",
                "Notwendigkeit & Verbot",
                "Modalverben für Pflichten, Ratschläge, fehlende Notwendigkeit und Verbote.",
                1,
                10,
                topic3_3
        );

        Slide s3_3_1_1 = new Slide("Modalverben der Notwendigkeit & des Verbots (Necessity & Prohibition)", 1, "GRAMMAR", lesson3_3_1);
        s3_3_1_1.setGermanText("Modalverb | Bedeutung / Meaning | Beispiel / Example\n" +
                "müssen | must / to have to | Ich muss um 8 Uhr im Büro sein. – I must be at the office at 8.\n" +
                "sollen | should / to be supposed to | Du sollst pünktlich zum Termin kommen. – You should arrive on time for the appointment.\n" +
                "nicht müssen | don't have to (not necessary) | Du musst nicht kommen. – You don't have to come.\n" +
                "nicht dürfen | must not / not allowed to | Du darfst hier nicht rauchen. – You must not smoke here.");
        s3_3_1_1.setEnglishTranslation("müssen: must / to have to -> Ich muss um 8 Uhr im Büro sein. (I must be at the office at 8.)\nsollen: should / to be supposed to -> Du sollst pünktlich zum Termin kommen. (You should arrive on time for the appointment.)\nnicht müssen: don't have to (not necessary) -> Du musst nicht kommen. (You don't have to come.)\nnicht dürfen: must not / not allowed to -> Du darfst hier nicht rauchen. (You must not smoke here.)");
        s3_3_1_1.setExplanation("Modal verbs modify the action of the main verb: 'müssen' denotes strict necessity, 'sollen' expresses advice or duty, 'nicht müssen' denotes lack of obligation, and 'nicht dürfen' denotes strict prohibition.");
        s3_3_1_1.setVocabulary("müssen (must/have to) | sollen (should/supposed to) | nicht müssen (don't have to) | dürfen (to be allowed to) | nicht dürfen (must not / forbidden) | rauchen (to smoke) | das Büro (office)");
        s3_3_1_1.setGrammarRule("Distinction:\n• 'nicht müssen' = lack of necessity (Du musst nicht kommen = You don't have to come)\n• 'nicht dürfen' = prohibition (Du darfst hier nicht rauchen = You must not smoke here)");
        s3_3_1_1.setExamples("Ich muss um 8 Uhr im Büro sein. (I must be at the office at 8.)\nDu sollst pünktlich zum Termin kommen. (You should arrive on time.)\nDu darfst hier nicht rauchen. (You must not smoke here.)\nDu musst die Hausaufgabe nicht heute machen. (You don't have to do the homework today.)");
        s3_3_1_1.setImportantNote("NEVER translate English 'must not' as 'müssen nicht'! In German, 'must not' is always 'nicht dürfen'!");
        lesson3_3_1.addSlide(s3_3_1_1);

        Slide s3_3_1_2 = new Slide("Konjugation: müssen, sollen & dürfen (Conjugation Table)", 2, "TABLE", lesson3_3_1);
        s3_3_1_2.setGermanText("Pronomen | müssen (must) | sollen (should) | dürfen (allowed to)\n" +
                "ich | muss | soll | darf\n" +
                "du | musst | sollst | darfst\n" +
                "er/sie/es | muss | soll | darf\n" +
                "wir | müssen | sollen | dürfen\n" +
                "ihr | müsst | sollt | dürft\n" +
                "sie/Sie | müssen | sollen | dürfen");
        s3_3_1_2.setEnglishTranslation("ich: muss | soll | darf (I must / should / am allowed)\ndu: musst | sollst | darfst (you must / should / are allowed)\ner/sie/es: muss | soll | darf (he/she/it must / should / is allowed)\nwir: müssen | sollen | dürfen (we must / should / are allowed)\nihr: müsst | sollt | dürft (you all must / should / are allowed)\nsie/Sie: müssen | sollen | dürfen (they/you formal must / should / are allowed)");
        s3_3_1_2.setExplanation("Notice the key modal verb pattern: 1st person singular (ich) and 3rd person singular (er/sie/es) have the exact same form and NO suffix ending!");
        s3_3_1_2.setVocabulary("ich muss (I must) | du musst (you must) | er muss (he must) | ich soll (I should) | ich darf (I may/am allowed) | du darfst (you may) | er darf (he may)");
        s3_3_1_2.setGrammarRule("Modal Verb Rules:\n1. 'ich' and 'er/sie/es' forms are identical and have no ending suffix (-t or -e).\n2. Singular stems have vowel change: müssen -> muss, dürfen -> darf.\n3. 'sollen' retains 'o' across all persons (ich soll, du sollst, etc.).");
        s3_3_1_2.setExamples("Er muss heute bis 18 Uhr arbeiten. (He must work until 18 o'clock today.)\nSoll ich den Arzt anrufen? (Should I call the doctor?)\nMan darf hier nicht laut sprechen. (One is not allowed to speak loudly here.)");
        s3_3_1_2.setImportantNote("Notice 'er muss' and 'er darf' do NOT have a '-t' at the end!");
        lesson3_3_1.addSlide(s3_3_1_2);
        topic3_3.addLesson(lesson3_3_1);

        // Lesson 3.3.2: Fähigkeit & Höflichkeit: können
        Lesson lesson3_3_2 = new Lesson(
                "Fähigkeit & Höflichkeit: können (Ability & Polite Requests)",
                "Fähigkeit & Höflichkeit: können",
                "Das Modalverb können für Fähigkeiten, Möglichkeiten und höfliche Bitten.",
                2,
                8,
                topic3_3
        );

        Slide s3_3_2_1 = new Slide("Das Modalverb können & Höfliche Bitten (Ability & Polite Requests)", 1, "GRAMMAR", lesson3_3_2);
        s3_3_2_1.setGermanText("Modalverb | Bedeutung / Meaning | Beispiel / Example\n" +
                "können | can / to be able to | Ich kann gut Deutsch sprechen. – I can speak German well.\n" +
                "nicht können | cannot / to be unable to | Er kann heute leider nicht kommen. – He unfortunately cannot come today.\n" +
                "Können Sie …? | Can you … ? (polite request) | Können Sie mir bitte helfen? – Can you please help me?\n\n" +
                "Konjugation: können\n" +
                "ich kann | du kannst | er/sie/es kann | wir können | ihr könnt | sie/Sie können");
        s3_3_2_1.setEnglishTranslation("können: can / to be able to -> Ich kann gut Deutsch sprechen. (I can speak German well.)\nnicht können: cannot / to be unable to -> Er kann heute leider nicht kommen. (He unfortunately cannot come today.)\nKönnen Sie …?: Can you … ? (polite request) -> Können Sie mir bitte helfen? (Can you please help me?)");
        s3_3_2_1.setExplanation("'können' expresses ability (Ich kann Deutsch sprechen), possibility (Er kann heute kommen), or when used at the start of a question with 'Sie', a polite request (Können Sie mir helfen?).");
        s3_3_2_1.setVocabulary("können (can/to be able to) | nicht können (cannot) | gut sprechen (to speak well) | leider (unfortunately) | helfen (to help) | die Hilfe (help)");
        s3_3_2_1.setGrammarRule("Conjugation of 'können':\n• ich kann\n• du kannst\n• er/sie/es kann\n• wir können\n• ihr könnt\n• sie/Sie können\nNotice: 'ö' becomes 'a' in the singular!");
        s3_3_2_1.setExamples("Ich kann gut Deutsch sprechen. (I can speak German well.)\nEr kann heute leider nicht kommen. (He unfortunately cannot come today.)\nKönnen Sie mir bitte helfen? (Can you please help me?)");
        s3_3_2_1.setImportantNote("Notice 'ich kann' and 'er kann' have NO ending suffix.");
        lesson3_3_2.addSlide(s3_3_2_1);
        topic3_3.addLesson(lesson3_3_2);

        // Lesson 3.3.3: Satzstellung mit Modalverben
        Lesson lesson3_3_3 = new Lesson(
                "Satzstellung mit Modalverben (Word Order & Sentence Bracket)",
                "Satzstellung mit Modalverben",
                "Die Satzklammer: Modalverb auf Position 2, Hauptverb im Infinitiv am Satzende.",
                3,
                10,
                topic3_3
        );

        Slide s3_3_3_1 = new Slide("Die Satzklammer: Modalverb + Infinitiv (The Sentence Bracket)", 1, "THEORY", lesson3_3_3);
        s3_3_3_1.setGermanText("Satzstellung / Word order:\nModalverb auf Position 2, Hauptverb (Infinitiv) am Satzende.\n\nBeispiel:\n„Ich muss den Bericht heute abgeben.“\n(Translation: “I have to submit the report today.”)\n\nSatzklammer-Struktur:\n[Position 1]: Ich (Subjekt)\n[Position 2]: muss (konjugiertes Modalverb)\n[Mittelfeld]: den Bericht heute\n[Satzende]: abgeben (Infinitiv des Hauptverbs)");
        s3_3_3_1.setEnglishTranslation("The modal verb takes position 2; the main verb (infinitive) goes to the end of the sentence.\nExample: “Ich muss den Bericht heute abgeben.” (I have to submit the report today.)");
        s3_3_3_1.setExplanation("German sentences with modal verbs create a grammatical 'bracket' (Satzklammer). The conjugated modal verb sits in Position 2, while the base infinitive verb is locked at the very end of the sentence.");
        s3_3_3_1.setVocabulary("die Satzstellung (word order) | die Satzklammer (sentence bracket) | der Bericht (report) | abgeben (to submit/hand in) | das Satzende (end of sentence)");
        s3_3_3_1.setGrammarRule("Satzklammer Rule:\n`[Subjekt/Pos 1] + [MODALVERB (Pos 2)] + [Objekt / Zeit / Ort] + [INFINITIV (Ende)]`\n\nIn Yes/No Questions:\n`[MODALVERB (Pos 1)] + [Subjekt] + [Objekt / Zeit / Ort] + [INFINITIV (Ende)]?`");
        s3_3_3_1.setExamples("Ich muss den Bericht heute abgeben. (I have to submit the report today.)\nEr kann heute leider nicht kommen. (He unfortunately cannot come today.)\nKönnen Sie mir bitte helfen? (Can you please help me?)\nDu sollst pünktlich zum Termin kommen. (You should arrive on time for the appointment.)");
        s3_3_3_1.setImportantNote("The main verb at the end of the sentence is ALWAYS in the infinitive (ending in -en). Never conjugate the final verb!");
        lesson3_3_3.addSlide(s3_3_3_1);

        Slide s3_3_3_2 = new Slide("Beispielsätze zur Satzstellung (Modal Verbs in Action)", 2, "EXAMPLE", lesson3_3_3);
        s3_3_3_2.setGermanText("1. Ich muss um 8 Uhr im Büro sein.\n2. Du sollst pünktlich zum Termin kommen.\n3. Du musst heute nicht kommen.\n4. Hier darf man nicht rauchen.\n5. Ich kann gut Deutsch sprechen.\n6. Er kann heute leider nicht kommen.\n7. Können Sie mir bitte helfen?");
        s3_3_3_2.setEnglishTranslation("1. I must be at the office at 8.\n2. You should arrive on time for the appointment.\n3. You don't have to come today.\n4. One is not allowed to smoke here.\n5. I can speak German well.\n6. He unfortunately cannot come today.\n7. Can you please help me?");
        s3_3_3_2.setExplanation("Examine how in every single sentence, the conjugated modal verb occupies Position 2 (or Position 1 in questions), and the infinitive verb (sein, kommen, rauchen, sprechen, helfen) sits at the very end.");
        s3_3_3_2.setVocabulary("im Büro sein (to be at the office) | pünktlich kommen (to come on time) | nicht rauchen (not to smoke) | Deutsch sprechen (to speak German) | mir helfen (to help me)");
        s3_3_3_2.setGrammarRule("Word order checklist:\n✓ Conjugated modal verb in position 2\n✓ All objects, adverbs, and time phrases in the middle\n✓ Unconjugated infinitive verb at the very end");
        s3_3_3_2.setExamples("Wir müssen morgen früh aufstehen. (We have to get up early tomorrow.)\nKannst du den Termin verschieben? (Can you reschedule the appointment?)\nSie dürfen hier parken. (You are allowed to park here.)");
        s3_3_3_2.setImportantNote("Notice that separable verbs stay whole as infinitives at the end when used with modal verbs (e.g. 'Ich muss früh aufstehen', not 'auf... stehen').");
        lesson3_3_3.addSlide(s3_3_3_2);
        topic3_3.addLesson(lesson3_3_3);
        mod3.addTopic(topic3_3);

        // ==========================================
        // TOPIC 3.4: Berufe & Arbeitsplatz (Professions & Workplace Vocabulary)
        // ==========================================
        Topic topic3_4 = new Topic(
                "Berufe & Arbeitsplatz (Professions & Workplace Vocabulary)",
                "Berufe & Arbeitsplatz",
                "Berufsbezeichnungen (männlich/weiblich), Arbeitsorte und nützliche Fragen zum Berufsleben.",
                4,
                mod3
        );

        // Lesson 3.4.1: Berufsbezeichnungen (männlich & weiblich)
        Lesson lesson3_4_1 = new Lesson(
                "Berufsbezeichnungen (männlich & weiblich) (Professions m/f)",
                "Berufsbezeichnungen (männlich & weiblich)",
                "Männliche und weibliche Formen von wichtigen Berufen aus dem Modul.",
                1,
                10,
                topic3_4
        );

        Slide s3_4_1_1 = new Slide("Berufe im Überblick (Professions Overview)", 1, "TABLE", lesson3_4_1);
        s3_4_1_1.setGermanText("Beruf (männlich / weiblich) | English\n" +
                "der Arzt / die Ärztin | the doctor (m/f)\n" +
                "der Lehrer / die Lehrerin | the teacher (m/f)\n" +
                "der Verkäufer / die Verkäuferin | the salesperson (m/f)\n" +
                "der Ingenieur / die Ingenieurin | the engineer (m/f)\n" +
                "der Koch / die Köchin | the cook / chef (m/f)\n" +
                "der Handwerker / die Handwerkerin | the tradesperson / craftsman (m/f)\n" +
                "der Polizist / die Polizistin | the police officer (m/f)\n" +
                "der Friseur / die Friseurin | the hairdresser (m/f)\n" +
                "der Kellner / die Kellnerin | the waiter / waitress (m/f)");
        s3_4_1_1.setEnglishTranslation("der Arzt / die Ärztin: the doctor (m/f)\nder Lehrer / die Lehrerin: the teacher (m/f)\nder Verkäufer / die Verkäuferin: the salesperson (m/f)\nder Ingenieur / die Ingenieurin: the engineer (m/f)\nder Koch / die Köchin: the cook / chef (m/f)\nder Handwerker / die Handwerkerin: the tradesperson / craftsman (m/f)\nder Polizist / die Polizistin: the police officer (m/f)\nder Friseur / die Friseurin: the hairdresser (m/f)\nder Kellner / die Kellnerin: the waiter / waitress (m/f)");
        s3_4_1_1.setExplanation("In German, professions are differentiated by gender. The female form is created by adding '-in' to the male form. Some short nouns also receive an umlaut (Arzt -> Ärztin, Koch -> Köchin).");
        s3_4_1_1.setVocabulary("der Arzt / die Ärztin (doctor) | der Lehrer / die Lehrerin (teacher) | der Verkäufer / die Verkäuferin (salesperson) | der Ingenieur / die Ingenieurin (engineer) | der Koch / die Köchin (cook) | der Handwerker / die Handwerkerin (craftsman) | der Polizist / die Polizistin (police officer) | der Friseur / die Friseurin (hairdresser) | der Kellner / die Kellnerin (waiter/waitress)");
        s3_4_1_1.setGrammarRule("Formation of Female Professions:\n• Regular: der [Beruf] -> die [Beruf] + in (Lehrer -> Lehrerin, Verkäufer -> Verkäuferin, Kellner -> Kellnerin)\n• With Umlaut: Arzt -> Ärztin, Koch -> Köchin");
        s3_4_1_1.setExamples("Mein Vater ist Ingenieur und meine Mutter ist Ärztin. (My father is an engineer and my mother is a doctor.)\nFrau Müller arbeitet als Lehrerin. (Ms. Müller works as a teacher.)\nDer Kellner bringt die Speisekarte. (The waiter brings the menu.)");
        s3_4_1_1.setImportantNote("When stating your profession in German, do NOT use an article: say 'Ich bin Arzt' or 'Ich bin Lehrerin' (never 'Ich bin ein Arzt').");
        lesson3_4_1.addSlide(s3_4_1_1);
        topic3_4.addLesson(lesson3_4_1);

        // Lesson 3.4.2: Typische Arbeitsorte
        Lesson lesson3_4_2 = new Lesson(
                "Typische Arbeitsorte (Workplaces & Prepositions)",
                "Typische Arbeitsorte",
                "Wo arbeiten die Berufe? Arbeitsorte und die passenden Präpositionen.",
                2,
                8,
                topic3_4
        );

        Slide s3_4_2_1 = new Slide("Berufe und ihre typischen Arbeitsorte (Professions & Workplaces)", 1, "TABLE", lesson3_4_2);
        s3_4_2_1.setGermanText("Beruf | Arbeitsort / Workplace\n" +
                "der Arzt / die Ärztin | arbeitet im Krankenhaus – works in the hospital\n" +
                "der Lehrer / die Lehrerin | unterrichtet in der Schule – teaches at school\n" +
                "der Verkäufer / die Verkäuferin | arbeitet im Geschäft – works in the shop\n" +
                "der Ingenieur / die Ingenieurin | arbeitet im Büro – works in the office\n" +
                "der Koch / die Köchin | arbeitet in der Küche – works in the kitchen\n" +
                "der Handwerker / die Handwerkerin | arbeitet auf der Baustelle – works on the construction site\n" +
                "der Polizist / die Polizistin | arbeitet bei der Polizei – works at the police\n" +
                "der Friseur / die Friseurin | arbeitet im Salon – works at the salon\n" +
                "der Kellner / die Kellnerin | arbeitet im Restaurant – works at the restaurant");
        s3_4_2_1.setEnglishTranslation("der Arzt / die Ärztin: works in the hospital\nder Lehrer / die Lehrerin: teaches at school\nder Verkäufer / die Verkäuferin: works in the shop\nder Ingenieur / die Ingenieurin: works in the office\nder Koch / die Köchin: works in the kitchen\nder Handwerker / die Handwerkerin: works on the construction site\nder Polizist / die Polizistin: works at the police\nder Friseur / die Friseurin: works at the salon\nder Kellner / die Kellnerin: works at the restaurant");
        s3_4_2_1.setExplanation("Workplace locations use dative prepositions matching the place noun: 'im' (in dem) for masculine/neuter rooms and buildings, 'in der' for feminine rooms/institutions, 'auf der' for sites, and 'bei der' for organizations.");
        s3_4_2_1.setVocabulary("das Krankenhaus (hospital) | die Schule (school) | das Geschäft (shop) | das Büro (office) | die Küche (kitchen) | die Baustelle (construction site) | die Polizei (police) | der Salon (salon) | das Restaurant (restaurant) | unterrichten (to teach)");
        s3_4_2_1.setGrammarRule("Workplace Preposition Pairs:\n• im Krankenhaus / im Geschäft / im Büro / im Salon / im Restaurant (in + dem)\n• in der Schule / in der Küche (in + der)\n• auf der Baustelle (auf + der)\n• bei der Polizei (bei + der)");
        s3_4_2_1.setExamples("Die Ärztin arbeitet im Krankenhaus. (The doctor works in the hospital.)\nDer Lehrer unterrichtet in der Schule. (The teacher teaches at school.)\nDer Handwerker arbeitet auf der Baustelle. (The craftsman works on the construction site.)\nDer Polizist arbeitet bei der Polizei. (The police officer works at the police.)");
        s3_4_2_1.setImportantNote("Notice that 'unterrichten' (to teach) is used specifically for teachers in schools, while other professions use 'arbeiten' (to work).");
        lesson3_4_2.addSlide(s3_4_2_1);
        topic3_4.addLesson(lesson3_4_2);

        // Lesson 3.4.3: Nützliche Fragen zum Beruf
        Lesson lesson3_4_3 = new Lesson(
                "Nützliche Fragen zum Beruf (Questions about Professions)",
                "Nützliche Fragen zum Beruf",
                "Wichtige Fragen und Redemittel für Gespräche über Beruf, Tätigkeit und Arbeitsplatz.",
                3,
                8,
                topic3_4
        );

        Slide s3_4_3_1 = new Slide("Fragen & Antworten zum Berufsleben (Asking About Professions)", 1, "DIALOGUE", lesson3_4_3);
        s3_4_3_1.setGermanText("Deutsch | English\n" +
                "Was sind Sie von Beruf? | What is your profession?\n" +
                "Was machen Sie beruflich? | What do you do for a living?\n" +
                "Wo arbeiten Sie? | Where do you work?\n" +
                "Ich arbeite als … | I work as a …\n" +
                "Ich bin von Beruf … | I am a … by profession\n" +
                "Seit wann arbeiten Sie dort? | Since when have you been working there?");
        s3_4_3_1.setEnglishTranslation("Was sind Sie von Beruf? (What is your profession?)\nWas machen Sie beruflich? (What do you do for a living?)\nWo arbeiten Sie? (Where do you work?)\nIch arbeite als … (I work as a …)\nIch bin von Beruf … (I am a … by profession)\nSeit wann arbeiten Sie dort? (Since when have you been working there?)");
        s3_4_3_1.setExplanation("Standard conversational phrases in German for networking, introductions, and workplace small talk.");
        s3_4_3_1.setVocabulary("von Beruf (by profession) | beruflich (for a living / professionally) | arbeiten als (to work as) | seit wann (since when) | dort (there)");
        s3_4_3_1.setGrammarRule("Three Ways to State Your Job:\n1. Ich bin [Beruf] von Beruf. (Ich bin Ingenieur von Beruf.)\n2. Ich arbeite als [Beruf]. (Ich arbeite als Lehrerin.)\n3. Ich arbeite bei [Firma/Institution]. (Ich arbeite bei Siemens / bei der Polizei.)");
        s3_4_3_1.setExamples("A: Was machen Sie beruflich?\nB: Ich bin Ärztin von Beruf.\nA: Wo arbeiten Sie?\nB: Ich arbeite im Krankenhaus in Berlin.\nA: Seit wann arbeiten Sie dort?\nB: Seit drei Jahren.");
        s3_4_3_1.setImportantNote("Both 'Was sind Sie von Beruf?' and 'Was machen Sie beruflich?' are equally polite and interchangeable.");
        lesson3_4_3.addSlide(s3_4_3_1);
        topic3_4.addLesson(lesson3_4_3);
        mod3.addTopic(topic3_4);

        // ==========================================
        // TOPIC 3.5: Gesundheit, Körperteile & Gefühle
        // ==========================================
        Topic topic3_5 = new Topic(
                "Gesundheit, Körperteile & Gefühle (Health, Body Parts & Feelings)",
                "Gesundheit, Körperteile & Gefühle",
                "Wortschatz für Körperteile, Gefühle/Befinden und typische Sätze beim Arzt.",
                5,
                mod3
        );

        // Lesson 3.5.1: Körperteile
        Lesson lesson3_5_1 = new Lesson(
                "Körperteile (Body Parts)",
                "Körperteile",
                "Die wichtigsten Körperteile des Menschen mit ihren Artikeln.",
                1,
                8,
                topic3_5
        );

        Slide s3_5_1_1 = new Slide("Der menschliche Körper (The Human Body)", 1, "TABLE", lesson3_5_1);
        s3_5_1_1.setGermanText("Körperteil | English\n" +
                "der Kopf | the head\n" +
                "der Bauch | the stomach\n" +
                "der Rücken | the back\n" +
                "das Bein | the leg\n" +
                "der Arm | the arm\n" +
                "der Hals | the throat / neck\n" +
                "das Herz | the heart\n" +
                "das Ohr | the ear\n" +
                "die Hand | the hand\n" +
                "der Fuß | the foot");
        s3_5_1_1.setEnglishTranslation("der Kopf (the head) | der Bauch (the stomach) | der Rücken (the back) | das Bein (the leg) | der Arm (the arm) | der Hals (the throat / neck) | das Herz (the heart) | das Ohr (the ear) | die Hand (the hand) | der Fuß (the foot)");
        s3_5_1_1.setExplanation("Essential body part vocabulary needed when visiting the doctor, describing aches and pains, or discussing fitness and health.");
        s3_5_1_1.setVocabulary("der Kopf (head) | der Bauch (stomach) | der Rücken (back) | das Bein (leg) | der Arm (arm) | der Hals (throat/neck) | das Herz (heart) | das Ohr (ear) | die Hand (hand) | der Fuß (foot)");
        s3_5_1_1.setGrammarRule("Genders of Body Parts:\n• Masculine (der): der Kopf, der Bauch, der Rücken, der Arm, der Hals, der Fuß\n• Neuter (das): das Bein, das Herz, das Ohr\n• Feminine (die): die Hand");
        s3_5_1_1.setExamples("Mein Kopf tut weh. (My head hurts.)\nEr hat Schmerzen am Rücken. (He has pain in his back.)\nSie hat die Hand verletzt. (She injured her hand.)");
        s3_5_1_1.setImportantNote("Notice that most body parts in this list are masculine ('der'). 'Die Hand' is the only feminine noun.");
        lesson3_5_1.addSlide(s3_5_1_1);
        topic3_5.addLesson(lesson3_5_1);

        // Lesson 3.5.2: Gefühle & Befinden
        Lesson lesson3_5_2 = new Lesson(
                "Gefühle & Befinden (Feelings & Condition)",
                "Gefühle & Befinden",
                "Adjektive zur Beschreibung des körperlichen und seelischen Zustands.",
                2,
                8,
                topic3_5
        );

        Slide s3_5_2_1 = new Slide("Gefühle und körperliches Befinden (Feelings & Physical State)", 1, "VOCABULARY", lesson3_5_2);
        s3_5_2_1.setGermanText("Deutsch | English\n" +
                "müde | tired\n" +
                "krank | sick\n" +
                "gesund | healthy\n" +
                "erkältet | having a cold\n" +
                "nervös | nervous\n" +
                "glücklich | happy\n" +
                "traurig | sad\n" +
                "gestresst | stressed");
        s3_5_2_1.setEnglishTranslation("müde (tired) | krank (sick) | gesund (healthy) | erkältet (having a cold) | nervös (nervous) | glücklich (happy) | traurig (sad) | gestresst (stressed)");
        s3_5_2_1.setExplanation("Use these adjectives with 'sein' (to be) or 'sich fühlen' (to feel) to describe your state of health and emotions.");
        s3_5_2_1.setVocabulary("müde (tired) | krank (sick) | gesund (healthy) | erkältet (having a cold) | nervös (nervous) | glücklich (happy) | traurig (sad) | gestresst (stressed)");
        s3_5_2_1.setGrammarRule("Grammatical Constructions:\n• 'Ich bin [Adjektiv]' -> Ich bin müde / krank / gesund / gestresst.\n• 'Ich fühle mich [Adjektiv]' -> Ich fühle mich glücklich / nicht wohl / traurig.");
        s3_5_2_1.setExamples("Ich bin heute sehr müde und gestresst. (I am very tired and stressed today.)\nEr ist erkältet und bleibt zu Hause. (He has a cold and stays at home.)\nWir fühlen uns gesund und glücklich. (We feel healthy and happy.)");
        s3_5_2_1.setImportantNote("'erkältet' specifically means having a common cold. 'krank' is the general term for being sick/ill.");
        lesson3_5_2.addSlide(s3_5_2_1);
        topic3_5.addLesson(lesson3_5_2);

        // Lesson 3.5.3: Beim Arzt & Beschwerden
        Lesson lesson3_5_3 = new Lesson(
                "Beim Arzt: Beschwerden & Ratschläge (At the Doctor's)",
                "Beim Arzt",
                "Wichtige Redemittel für den Arztbesuch, Symptome und ärztliche Empfehlungen.",
                3,
                10,
                topic3_5
        );

        Slide s3_5_3_1 = new Slide("Redemittel beim Arzt (Phrases at the Doctor's)", 1, "DIALOGUE", lesson3_5_3);
        s3_5_3_1.setGermanText("Deutsch | English\n" +
                "Was fehlt Ihnen? | What's wrong with you? / What seems to be the problem?\n" +
                "Wo tut es weh? | Where does it hurt?\n" +
                "Mir tut der Kopf weh. | My head hurts.\n" +
                "Ich habe Halsschmerzen und Fieber. | I have a sore throat and fever.\n" +
                "Ich fühle mich seit gestern nicht wohl. | I haven't felt well since yesterday.\n" +
                "Sie müssen sich ausruhen und viel trinken. | You must rest and drink a lot.\n" +
                "Wie fühlst du dich heute? | How do you feel today?\n" +
                "Was tut dir weh? | What hurts?\n" +
                "Musst du zum Arzt gehen? | Do you have to go to the doctor?\n" +
                "Kannst du heute Sport machen? | Can you do sports today?");
        s3_5_3_1.setEnglishTranslation("Was fehlt Ihnen? (What's wrong with you?)\nWo tut es weh? (Where does it hurt?)\nMir tut der Kopf weh. (My head hurts.)\nIch habe Halsschmerzen und Fieber. (I have a sore throat and fever.)\nIch fühle mich seit gestern nicht wohl. (I haven't felt well since yesterday.)\nSie müssen sich ausruhen und viel trinken. (You must rest and drink a lot.)\nWie fühlst du dich heute? (How do you feel today?)\nWas tut dir weh? (What hurts?)\nMusst du zum Arzt gehen? (Do you have to go to the doctor?)\nKannst du heute Sport machen? (Can you do sports today?)");
        s3_5_3_1.setExplanation("The doctor-patient dialogue is a fundamental communication scenario in German. Learn to describe symptoms and understand medical advice.");
        s3_5_3_1.setVocabulary("fehlen (to be wrong/missing) | wehtun (to hurt) | die Halsschmerzen (sore throat) | das Fieber (fever) | sich wohl fühlen (to feel well) | sich ausruhen (to rest) | viel trinken (to drink a lot)");
        s3_5_3_1.setGrammarRule("Expressing Pain:\n• 'Mir tut [Körperteil] weh.' -> Mir tut der Kopf / der Bauch / der Rücken weh.\n• 'Ich habe [Schmerzen / Fieber].' -> Ich habe Halsschmerzen / Kopfschmerzen / Fieber.");
        s3_5_3_1.setExamples("Arzt: Guten Tag! Was fehlt Ihnen?\nPatient: Guten Tag. Mir tut der Hals weh und ich fühle mich seit gestern nicht wohl.\nArzt: Haben Sie auch Fieber?\nPatient: Ja, und mir tut der Kopf weh.\nArzt: Sie müssen sich ausruhen und viel trinken.");
        s3_5_3_1.setImportantNote("'Was fehlt Ihnen?' literally translates to 'What is missing from you?' and is the idiomatic standard greeting from German doctors.");
        lesson3_5_3.addSlide(s3_5_3_1);
        topic3_5.addLesson(lesson3_5_3);
        mod3.addTopic(topic3_5);

        // ==========================================
        // TOPIC 3.6: Das Perfekt (The Perfect Tense)
        // ==========================================
        Topic topic3_6 = new Topic(
                "Das Perfekt (The Perfect Tense: Past Actions)",
                "Das Perfekt",
                "Bildung des Perfekts mit haben/sein + Partizip II, häufige Partizipien und die Satzklammer.",
                6,
                mod3
        );

        // Lesson 3.6.1: Bildung: haben vs. sein
        Lesson lesson3_6_1 = new Lesson(
                "Bildung des Perfekts: haben vs. sein (Formation: haben vs. sein)",
                "Bildung: haben vs. sein",
                "Wann verwendet man haben und wann sein als Hilfsverb im Perfekt?",
                1,
                10,
                topic3_6
        );

        Slide s3_6_1_1 = new Slide("Hilfsverben im Perfekt (Auxiliary Verbs: haben & sein)", 1, "THEORY", lesson3_6_1);
        s3_6_1_1.setGermanText("Hilfsverb | Regel / Rule | Beispiel / Example\n" +
                "haben | to have (auxiliary for most verbs) | Ich habe gearbeitet. – I have worked / I worked.\n" +
                "sein | to be (auxiliary for motion/change of state) | Ich bin zum Arzt gegangen. – I went to the doctor.");
        s3_6_1_1.setEnglishTranslation("haben: to have (auxiliary for most verbs) -> Ich habe gearbeitet. (I have worked / I worked.)\nsein: to be (auxiliary for motion/change of state) -> Ich bin zum Arzt gegangen. (I went to the doctor.)");
        s3_6_1_1.setExplanation("The Perfect Tense (Das Perfekt) is the main spoken past tense in German. It is formed using a present tense conjugated auxiliary verb (haben or sein) + the Past Participle (Partizip II) at the end of the sentence.");
        s3_6_1_1.setVocabulary("das Hilfsverb (auxiliary verb) | das Partizip II (past participle) | die Bewegung (movement/motion) | die Zustandsänderung (change of state)");
        s3_6_1_1.setGrammarRule("Auxiliary Selection Rules:\n1. haben: Used for most verbs, including verbs with direct objects (transitive), reflexive verbs, and stationary verbs (arbeiten, machen, essen, sehen, haben).\n2. sein: Used for verbs of movement from one place to another (gehen, kommen, fahren), change of state (aufstehen, einschlafen), and 'sein' / 'bleiben'.");
        s3_6_1_1.setExamples("Ich habe gearbeitet. (I worked.)\nIch bin zum Arzt gegangen. (I went to the doctor.)\nEr ist nach Hause gekommen. (He came home.)\nWir haben eine Pizza gegessen. (We ate a pizza.)");
        s3_6_1_1.setImportantNote("In conversational German, the Perfekt is used almost exclusively for talking about the past!");
        lesson3_6_1.addSlide(s3_6_1_1);
        topic3_6.addLesson(lesson3_6_1);

        // Lesson 3.6.2: Partizip II — Häufige Formen
        Lesson lesson3_6_2 = new Lesson(
                "Partizip II — Häufige Formen (Common Past Participles)",
                "Partizip II — Häufige Formen",
                "Die wichtigsten Partizip II Formen aus dem Modul mit Beispielen.",
                2,
                10,
                topic3_6
        );

        Slide s3_6_2_1 = new Slide("Häufige Partizipien im Überblick (Common Past Participles Table)", 1, "TABLE", lesson3_6_2);
        s3_6_2_1.setGermanText("Infinitiv → Partizip II | English\n" +
                "arbeiten → gearbeitet | to work → worked\n" +
                "machen → gemacht | to do/make → done/made\n" +
                "gehen → gegangen | to go → gone\n" +
                "sprechen → gesprochen | to speak → spoken\n" +
                "anrufen → angerufen | to call → called\n" +
                "sich fühlen → sich gefühlt | to feel → felt\n" +
                "kommen → gekommen | to come → come\n" +
                "sehen → gesehen | to see → seen\n" +
                "essen → gegessen | to eat → eaten\n" +
                "haben → gehabt | to have → had");
        s3_6_2_1.setEnglishTranslation("arbeiten → gearbeitet (to work → worked)\nmachen → gemacht (to do/make → done/made)\ngehen → gegangen (to go → gone)\nsprechen → gesprochen (to speak → spoken)\nanrufen → angerufen (to call → called)\nsich fühlen → sich gefühlt (to feel → felt)\nkommen → gekommen (to come → come)\nsehen → gesehen (to see → seen)\nessen → gegessen (to eat → eaten)\nhaben → gehabt (to have → had)");
        s3_6_2_1.setExplanation("Mastering the 10 core past participles from Module III allows you to express daily activities, phone calls, meals, and appointments in the past.");
        s3_6_2_1.setVocabulary("gearbeitet (worked) | gemacht (done/made) | gegangen (gone) | gesprochen (spoken) | angerufen (called) | gefühlt (felt) | gekommen (come) | gesehen (seen) | gegessen (eaten) | gehabt (had)");
        s3_6_2_1.setGrammarRule("Partizip II Formation Patterns:\n• Regular (weak) verbs: ge- + stem + -(e)t (ge-arbeit-et, ge-mach-t, ge-fühl-t)\n• Irregular (strong) verbs: ge- + (stem vowel change) + -en (ge-gang-en, ge-sproch-en, ge-seh-en, ge-gess-en)\n• Separable verbs: prefix + ge- + stem + ending (an-ge-ruf-en)\n• haben: ge-hab-t");
        s3_6_2_1.setExamples("Ich habe gestern gearbeitet. (I worked yesterday.)\nWir haben Deutsch gesprochen. (We spoke German.)\nIch habe den Arzt angerufen. (I called the doctor.)\nEr hat einen Apfel gegessen. (He ate an apple.)");
        s3_6_2_1.setImportantNote("For separable verbs like 'anrufen', the '-ge-' is inserted between the prefix and the stem: an-ge-rufen!");
        lesson3_6_2.addSlide(s3_6_2_1);
        topic3_6.addLesson(lesson3_6_2);

        // Lesson 3.6.3: Die Satzklammer im Perfekt
        Lesson lesson3_6_3 = new Lesson(
                "Die Satzklammer im Perfekt (The Sentence Bracket in the Past Tense)",
                "Die Satzklammer im Perfekt",
                "Satzstruktur im Perfekt: Hilfsverb auf Position 2, Partizip II am Satzende.",
                3,
                8,
                topic3_6
        );

        Slide s3_6_3_1 = new Slide("Satzstellung & Die Satzklammer (Sentence Bracket in the Perfect Tense)", 1, "THEORY", lesson3_6_3);
        s3_6_3_1.setGermanText("Satzklammer / Sentence bracket:\nhaben/sein auf Position 2, Partizip II am Satzende.\n\nBeispiel aus dem Modul:\n„Gestern habe ich einen Termin beim Arzt gehabt.“\n(Translation: “Yesterday I had an appointment at the doctor's.”)\n\nSatzklammer-Analyse:\n[Position 1]: Gestern (Zeitangabe)\n[Position 2]: habe (Hilfsverb haben konjugiert)\n[Subjekt]: ich\n[Mittelfeld]: einen Termin beim Arzt\n[Satzende]: gehabt (Partizip II)");
        s3_6_3_1.setEnglishTranslation("haben/sein takes position 2, the past participle goes to the end of the sentence.\nExample: “Yesterday I had an appointment at the doctor's.”");
        s3_6_3_1.setExplanation("The auxiliary verb (haben or sein) is placed in Position 2 and conjugated for the subject. The Partizip II is placed at the very end of the clause, framing all objects and details in between.");
        s3_6_3_1.setVocabulary("die Satzklammer (sentence bracket) | gestern (yesterday) | der Termin (appointment) | beim Arzt (at the doctor's) | gehabt (had)");
        s3_6_3_1.setGrammarRule("Sentence Structure Formula:\n`[Pos 1] + [HABEN / SEIN (Pos 2)] + [Subjekt / Mittelfeld] + [PARTIZIP II (Ende)]`\n\nQuestions:\n`[HABEN / SEIN (Pos 1)] + [Subjekt] + [Mittelfeld] + [PARTIZIP II (Ende)]?`");
        s3_6_3_1.setExamples("Gestern habe ich einen Termin beim Arzt gehabt. (Yesterday I had an appointment at the doctor's.)\nIch bin heute um 8 Uhr zur Arbeit gegangen. (I went to work today at 8 o'clock.)\nHast du deine Hausaufgaben gemacht? (Did you do your homework?)");
        s3_6_3_1.setImportantNote("No matter how long the sentence is, the Partizip II always anchors the end of the main clause!");
        lesson3_6_3.addSlide(s3_6_3_1);
        topic3_6.addLesson(lesson3_6_3);
        mod3.addTopic(topic3_6);

        // ==========================================
        // TOPIC 3.7: Aktivitäten (Classroom Activities)
        // ==========================================
        Topic topic3_7 = new Topic(
                "Aktivitäten (Classroom Activities & Interactive Practice)",
                "Aktivitäten",
                "Wortschatz und Redemittel für die drei praktischen Übungen des Moduls.",
                7,
                mod3
        );

        // Lesson 3.7.1: Aktivität 1 — Zeitbasiertes Rollenspiel
        Lesson lesson3_7_1 = new Lesson(
                "Aktivität 1: Zeitbasiertes Rollenspiel (Time-based Role-play)",
                "Aktivität 1: Zeitbasiertes Rollenspiel",
                "Redemittel für die Terminabsprache und Kalenderabstimmung im Partnerspiel.",
                1,
                8,
                topic3_7
        );

        Slide s3_7_1_1 = new Slide("Wortschatz & Rollenspiel (Role-Play Vocabulary & Phrases)", 1, "DIALOGUE", lesson3_7_1);
        s3_7_1_1.setGermanText("Deutsch | English\n" +
                "Partner A ruft an, um einen Termin zu vereinbaren. | Partner A calls to arrange an appointment.\n" +
                "Partner B schaut in den Kalender. | Partner B checks the calendar.\n" +
                "eine Uhrzeit vorschlagen | to suggest a time\n" +
                "die Rollen tauschen | to swap roles");
        s3_7_1_1.setEnglishTranslation("Partner A ruft an, um einen Termin zu vereinbaren. (Partner A calls to arrange an appointment.)\nPartner B schaut in den Kalender. (Partner B checks the calendar.)\neine Uhrzeit vorschlagen (to suggest a time)\ndie Rollen tauschen (to swap roles)");
        s3_7_1_1.setExplanation("Step-by-step vocabulary and instructions for practicing telephone scheduling dialogues in pairs.");
        s3_7_1_1.setVocabulary("anrufen (to call) | der Kalender (calendar) | vorschlagen (to suggest) | die Rollen tauschen (to swap roles)");
        s3_7_1_1.setGrammarRule("'vorschlagen' is a separable verb:\n• 'Ich schlage 14 Uhr vor.' (I suggest 2 p.m.)\n• 'Wir tauschen die Rollen.' (We swap roles.)");
        s3_7_1_1.setExamples("Partner A: Guten Tag, ich möchte einen Termin vereinbaren.\nPartner B: Ich schaue in den Kalender. Passt Ihnen Mittwoch um 10 Uhr?\nPartner A: Ja, 10 Uhr passt mir sehr gut!");
        s3_7_1_1.setImportantNote("Remember to swap roles so both partners practice asking and responding.");
        lesson3_7_1.addSlide(s3_7_1_1);
        topic3_7.addLesson(lesson3_7_1);

        // Lesson 3.7.2: Aktivität 2 — Berufe-Bingo
        Lesson lesson3_7_2 = new Lesson(
                "Aktivität 2: Berufe-Bingo (Profession Bingo)",
                "Aktivität 2: Berufe-Bingo",
                "Wortschatz für das Zuordnungsspiel von Tätigkeiten zu Berufen.",
                2,
                8,
                topic3_7
        );

        Slide s3_7_2_1 = new Slide("Berufe-Bingo Redemittel & Tätigkeiten (Profession Bingo Phrases)", 1, "VOCABULARY", lesson3_7_2);
        s3_7_2_1.setGermanText("Deutsch | English\n" +
                "die Bingo-Karte | the bingo card\n" +
                "die Beschreibung | the description\n" +
                "die Tätigkeit | the activity / task\n" +
                "dem passenden Beruf zuordnen | to match to the correct profession");
        s3_7_2_1.setEnglishTranslation("die Bingo-Karte (the bingo card)\ndie Beschreibung (the description)\ndie Tätigkeit (the activity / task)\ndem passenden Beruf zuordnen (to match to the correct profession)");
        s3_7_2_1.setExplanation("An engaging matching game where learners connect job activities and workplaces to the corresponding professional title.");
        s3_7_2_1.setVocabulary("die Bingo-Karte (bingo card) | die Beschreibung (description) | die Tätigkeit (task/activity) | zuordnen (to match/assign)");
        s3_7_2_1.setGrammarRule("'zuordnen' governs the Dative case for the recipient/target: 'dem passenden Beruf (Dative m) zuordnen'.");
        s3_7_2_1.setExamples("Beschreibung: Arbeitet im Krankenhaus und untersucht Patienten.\nZuordnung: Das ist die Ärztin oder der Arzt!\n\nBeschreibung: Unterrichtet Schüler in der Schule.\nZuordnung: Das ist der Lehrer oder die Lehrerin!");
        s3_7_2_1.setImportantNote("'die Tätigkeit' refers to the concrete duties performed on the job.");
        lesson3_7_2.addSlide(s3_7_2_1);
        topic3_7.addLesson(lesson3_7_2);

        // Lesson 3.7.3: Aktivität 3 — Gesundheits-Fragebogen
        Lesson lesson3_7_3 = new Lesson(
                "Aktivität 3: Gesundheits-Fragebogen (Health Questionnaire)",
                "Aktivität 3: Gesundheits-Fragebogen",
                "Partnerinterview über Gesundheit, Wohlbefinden und Arztbesuche.",
                3,
                8,
                topic3_7
        );

        Slide s3_7_3_1 = new Slide("Gesundheits-Fragebogen Redemittel (Health Questionnaire Phrases)", 1, "DIALOGUE", lesson3_7_3);
        s3_7_3_1.setGermanText("Deutsch | English\n" +
                "die Partnerarbeit | pair work\n" +
                "sich gegenseitig befragen | to ask each other\n" +
                "das Befinden | the (state of) well-being / condition\n" +
                "die Antwort geben | to give the answer");
        s3_7_3_1.setEnglishTranslation("die Partnerarbeit (pair work)\nsich gegenseitig befragen (to ask each other)\ndas Befinden (the well-being / condition)\ndie Antwort geben (to give the answer)");
        s3_7_3_1.setExplanation("Conducting health questionnaires in pairs allows students to practice asking about symptoms and formulating sympathetic responses.");
        s3_7_3_1.setVocabulary("die Partnerarbeit (pair work) | sich gegenseitig befragen (to interview each other) | das Befinden (well-being) | die Antwort geben (to give the answer)");
        s3_7_3_1.setGrammarRule("Reciprocal reflexive verb: 'sich gegenseitig befragen' (to interview each other).");
        s3_7_3_1.setExamples("Frage 1: Wie fühlst du dich heute?\nAntwort: Ich fühle mich gesund und fit.\n\nFrage 2: Was tut dir weh?\nAntwort: Mir tut der Kopf ein bisschen weh.\n\nFrage 3: Musst du zum Arzt gehen?\nAntwort: Nein, ich muss mich nur ausruhen.");
        s3_7_3_1.setImportantNote("Always answer in complete German sentences to reinforce grammar structures.");
        lesson3_7_3.addSlide(s3_7_3_1);
        topic3_7.addLesson(lesson3_7_3);
        mod3.addTopic(topic3_7);

        moduleRepository.save(mod3);
    }

    private void initModule4() {
        Optional<Module> existingOpt = moduleRepository.findByCode("MODULE_4");
        Module mod4;
        if (existingOpt.isPresent()) {
            mod4 = existingOpt.get();
            if (mod4.getTopics().size() == 6) {
                return;
            }
            userProgressRepository.deleteAll();
            mod4.getTopics().clear();
        } else {
            mod4 = new Module();
            mod4.setCode("MODULE_4");
        }

        mod4.setTitle("Module IV: Free Time, Holidays & Essential Grammar");
        mod4.setGermanTitle("Modul IV: Freizeit, Feste & Grammatik");
        mod4.setDescription("Ein vollständiger Leitfaden zum Wiederholen des Moduls. Master leisure activities & hobbies, separable verbs (trennbare Verben), the imperative mood (Der Imperativ), modal verbs (können, müssen, wollen, sollen, dürfen, möchten), travel & weather, holiday celebrations, practical travel dialogues, and comprehensive grammar review drills.");
        mod4.setLevel("A2.1");
        mod4.setOrderIndex(4);
        mod4.setActive(true);

        // ==========================================
        // TOPIC 4.1: Freizeit & Hobbys
        // ==========================================
        Topic topic4_1 = new Topic(
                "Freizeit & Hobbys (Free Time & Hobbies)",
                "Freizeit & Hobbys",
                "Wortschatz und Redemittel für Freizeitaktivitäten und Hobbys.",
                1,
                mod4
        );

        // Lesson 4.1.1: Hobbys: Kernvokabular & Beispielsätze
        Lesson lesson4_1_1 = new Lesson(
                "Hobbys: Kernvokabular & Beispielsätze (Hobbies Key Vocabulary & Examples)",
                "Hobbys: Kernvokabular & Beispielsätze",
                "Die wichtigsten Hobbys und Freizeitbeschäftigungen mit Beispielsätzen.",
                1,
                10,
                topic4_1
        );

        Slide s4_1_1_1 = new Slide("Hobbys im Überblick (Hobbies Overview)", 1, "TABLE", lesson4_1_1);
        s4_1_1_1.setGermanText("Hobby | English | Beispielsatz / Example Sentence\n" +
                "lesen | to read | Ich lese gern Bücher.\n" +
                "schwimmen | to swim | Er schwimmt jeden Tag.\n" +
                "wandern | to hike | Wir wandern am Wochenende.\n" +
                "Rad fahren | to cycle / ride a bike | Sie fährt gern Rad.\n" +
                "kochen | to cook | Ich koche gern italienisch.\n" +
                "malen | to paint | Mein Bruder malt Bilder.\n" +
                "Fußball spielen | to play soccer | Die Jungen spielen Fußball.\n" +
                "fotografieren | to photograph | Sie fotografiert die Natur.");
        s4_1_1_1.setEnglishTranslation("lesen: to read -> Ich lese gern Bücher. (I like reading books.)\n" +
                "schwimmen: to swim -> Er schwimmt jeden Tag. (He swims every day.)\n" +
                "wandern: to hike -> Wir wandern am Wochenende. (We hike on the weekend.)\n" +
                "Rad fahren: to cycle / ride a bike -> Sie fährt gern Rad. (She likes cycling.)\n" +
                "kochen: to cook -> Ich koche gern italienisch. (I like cooking Italian food.)\n" +
                "malen: to paint -> Mein Bruder malt Bilder. (My brother paints pictures.)\n" +
                "Fußball spielen: to play soccer -> Die Jungen spielen Fußball. (The boys play soccer.)\n" +
                "fotografieren: to photograph -> Sie fotografiert die Natur. (She photographs nature.)");
        s4_1_1_1.setExplanation("Essential German vocabulary and verbs to talk about personal hobbies, sports, and creative interests.");
        s4_1_1_1.setVocabulary("lesen (to read) | schwimmen (to swim) | wandern (to hike) | Rad fahren (to cycle) | kochen (to cook) | malen (to paint) | Fußball spielen (to play soccer) | fotografieren (to photograph)");
        s4_1_1_1.setGrammarRule("Stem vowel changes in present tense:\n• lesen -> du liest, er/sie/es liest (e -> ie)\n• Rad fahren -> du fährst Rad, er/sie/es fährt Rad (a -> ä)");
        s4_1_1_1.setExamples("Ich lese gern Bücher. (I like reading books.)\nEr schwimmt jeden Tag. (He swims every day.)\nWir wandern am Wochenende. (We hike on the weekend.)\nSie fährt gern Rad. (She likes cycling.)");
        s4_1_1_1.setImportantNote("'Rad fahren' (to ride a bike) is spelled as two separate words in modern German orthography.");
        lesson4_1_1.addSlide(s4_1_1_1);

        Slide s4_1_1_2 = new Slide("Beispielsätze zu Hobbys (Hobbies in Sentences)", 2, "EXAMPLE", lesson4_1_1);
        s4_1_1_2.setGermanText("1. Ich lese gern Bücher. — I like reading books.\n" +
                "2. Er schwimmt jeden Tag. — He swims every day.\n" +
                "3. Wir wandern am Wochenende. — We hike on the weekend.\n" +
                "4. Sie fährt gern Rad. — She likes cycling.\n" +
                "5. Ich koche gern italienisch. — I like cooking Italian food.\n" +
                "6. Mein Bruder malt Bilder. — My brother paints pictures.\n" +
                "7. Die Jungen spielen Fußball. — The boys play soccer.\n" +
                "8. Sie fotografiert die Natur. — She photographs nature.");
        s4_1_1_2.setEnglishTranslation("1. I like reading books.\n2. He swims every day.\n3. We hike on the weekend.\n4. She likes cycling.\n5. I like cooking Italian food.\n6. My brother paints pictures.\n7. The boys play soccer.\n8. She photographs nature.");
        s4_1_1_2.setExplanation("Notice how the adverb 'gern' follows directly after the conjugated verb to express that you like doing the action.");
        s4_1_1_2.setVocabulary("jeden Tag (every day) | am Wochenende (on the weekend) | italienisch (Italian) | das Bild (picture) | die Jungen (the boys) | die Natur (nature)");
        s4_1_1_2.setGrammarRule("Word order with 'gern': `[Subject] + [Verb] + gern + [Objects/Details]` (e.g. Ich koche gern italienisch).");
        s4_1_1_2.setExamples("Ich spiele gern Fußball. (I like playing soccer.)\nEr malt gern Landschaften. (He likes painting landscapes.)");
        s4_1_1_2.setImportantNote("In German, you do not use a separate verb like 'to like' for activities; you simply add 'gern' after the verb!");
        lesson4_1_1.addSlide(s4_1_1_2);
        topic4_1.addLesson(lesson4_1_1);

        // Lesson 4.1.2: Über die Freizeit sprechen
        Lesson lesson4_1_2 = new Lesson(
                "Über die Freizeit sprechen (Talking About Free Time)",
                "Über die Freizeit sprechen",
                "Typische Fragen und Redewendungen über Freizeit und Hobbys.",
                2,
                8,
                topic4_1
        );

        Slide s4_1_2_1 = new Slide("Redemittel zur Freizeit (Free Time Conversation Phrases)", 1, "DIALOGUE", lesson4_1_2);
        s4_1_2_1.setGermanText("Deutsch | English\n" +
                "Was machst du in deiner Freizeit? | What do you do in your free time?\n" +
                "Ich spiele gern Fußball. | I like playing soccer. (gern + verb = to like doing something)\n" +
                "Mein Hobby ist Fotografieren. | My hobby is photography.\n" +
                "Wie oft trainierst du? | How often do you train?\n" +
                "Ich habe wenig Freizeit. | I have little free time.");
        s4_1_2_1.setEnglishTranslation("Was machst du in deiner Freizeit? (What do you do in your free time?)\nIch spiele gern Fußball. (I like playing soccer.)\nMein Hobby ist Fotografieren. (My hobby is photography.)\nWie oft trainierst du? (How often do you train?)\nIch habe wenig Freizeit. (I have little free time.)");
        s4_1_2_1.setExplanation("Useful conversational questions and statements for asking others about their free time activities and expressing your own habits.");
        s4_1_2_1.setVocabulary("die Freizeit (free time) | in deiner Freizeit (in your free time) | das Hobby (hobby) | trainieren (to train/work out) | wie oft (how often) | wenig (little)");
        s4_1_2_1.setGrammarRule("Nominalized Verbs (Gerunds):\nWhen German verbs are turned into nouns (e.g. Fotografieren, Schwimmen, Lesen), they take neuter gender (das) and are always capitalized.");
        s4_1_2_1.setExamples("A: Was machst du in deiner Freizeit?\nB: Ich spiele gern Fußball und fahre Rad. Und du?\nA: Mein Hobby ist Fotografieren, aber ich habe wenig Freizeit.");
        s4_1_2_1.setImportantNote("'Wie oft' (How often) asks for frequency. Answers: jeden Tag (every day), oft (often), manchmal (sometimes), selten (rarely).");
        lesson4_1_2.addSlide(s4_1_2_1);
        topic4_1.addLesson(lesson4_1_2);
        mod4.addTopic(topic4_1);

        // ==========================================
        // TOPIC 4.2: Trennbare Verben
        // ==========================================
        Topic topic4_2 = new Topic(
                "Trennbare Verben (Separable Verbs)",
                "Trennbare Verben",
                "Das Prinzip der trennbaren Verben, Präfixe, Satzklammer und Nebensätze.",
                2,
                mod4
        );

        // Lesson 4.2.1: Trennbare Verben: Grundlagen & Regeln
        Lesson lesson4_2_1 = new Lesson(
                "Trennbare Verben: Grundlagen & Regeln (Separable Verbs: Foundations & Rules)",
                "Trennbare Verben: Grundlagen & Regeln",
                "Wie sich trennbare Verben im Satz trennen und verhalten.",
                1,
                10,
                topic4_2
        );

        Slide s4_2_1_1 = new Slide("Das Prinzip der trennbaren Verben (Principles of Separable Verbs)", 1, "THEORY", lesson4_2_1);
        s4_2_1_1.setGermanText("Häufige trennbare Vorsilben (Common Prefixes):\n" +
                "auf-, an-, aus-, ein-, mit-, fern-, zu-.\n\n" +
                "Regel (Rule):\n" +
                "1. Das konjugierte Verb bleibt auf Position 2 im Hauptsatz.\n" +
                "2. Die Vorsilbe (das Präfix) wandert ans absolute Satzende.\n" +
                "3. Satzbau: Subjekt + [konjugiertes Verb] + … + [Präfix].\n" +
                "4. In Nebensätzen (nach weil, dass, wenn …) bleiben Verb und Vorsilbe am Satzende zusammen.\n" +
                "5. Betonung: Die Betonung liegt immer auf dem Präfix (AUFstehen, EINkaufen, FERNsehen).\n\n" +
                "Beispiel:\n" +
                "aufstehen (Infinitiv) → Ich stehe um 7 Uhr auf. — “I get up at 7 o’clock.”");
        s4_2_1_1.setEnglishTranslation("Common separable prefixes: auf-, an-, aus-, ein-, mit-, fern-, zu-.\nRule: the conjugated verb stays in position 2 of the sentence; the prefix jumps to the very end.\nPattern: Subject + [conjugated verb] + … + [prefix].\nIn subordinate clauses (after weil, dass, wenn …) the verb and prefix stay joined together at the end.\nStress falls on the prefix in the infinitive, e.g. AUFstehen, EINkaufen, FERNsehen.\nExample: aufstehen (infinitive) -> Ich stehe um 7 Uhr auf — “I get up at 7 o’clock.”");
        s4_2_1_1.setExplanation("Separable verbs consist of a root verb and a prefix. In present tense main clauses, the prefix detaches and migrates to the very end of the sentence.");
        s4_2_1_1.setVocabulary("die Vorsilbe / das Präfix (prefix) | der Hauptsatz (main clause) | der Nebensatz (subordinate clause) | die Betonung (stress/accent)");
        s4_2_1_1.setGrammarRule("Structure Formulas:\n• Main clause: `[Subject] + [Verb (Pos 2)] + [Objects / Adverbs] + [Prefix (End)]`\n• Subordinate clause: `... weil ich jeden Tag um 7 Uhr aufstehe.` (joined at the end!)");
        s4_2_1_1.setExamples("aufstehen: Ich stehe um 7 Uhr auf. (I get up at 7 o'clock.)\neinkaufen: Wir kaufen samstags ein. (We go shopping on Saturdays.)");
        s4_2_1_1.setImportantNote("The prefix in separable verbs always carries the main acoustic stress when spoken (AUF-stehen, EIN-kaufen).");
        lesson4_2_1.addSlide(s4_2_1_1);
        topic4_2.addLesson(lesson4_2_1);

        // Lesson 4.2.2: Trennbare Verben in Aktion
        Lesson lesson4_2_2 = new Lesson(
                "Trennbare Verben in Aktion (Separable Verbs in Action)",
                "Trennbare Verben in Aktion",
                "Beispielsätze und Konjugationen trennbarer Verben im Alltag.",
                2,
                10,
                topic4_2
        );

        Slide s4_2_2_1 = new Slide("Trennbare Verben Beispieltabelle (Separable Verbs Table)", 1, "TABLE", lesson4_2_2);
        s4_2_2_1.setGermanText("Infinitiv | Satz (Deutsch) | English Translation\n" +
                "fernsehen | Ich sehe abends fern. | I watch TV in the evenings.\n" +
                "aufstehen | Ich stehe um 7 Uhr auf. | I get up at 7 o’clock.\n" +
                "einkaufen | Wir kaufen samstags ein. | We go shopping on Saturdays.\n" +
                "mitkommen | Kommst du mit? | Are you coming along?\n" +
                "anfangen | Der Film fängt um 8 Uhr an. | The film starts at 8 o’clock.\n" +
                "ausgehen | Ich gehe heute Abend aus. | I’m going out tonight.");
        s4_2_2_1.setEnglishTranslation("fernsehen: Ich sehe abends fern. (I watch TV in the evenings.)\naufstehen: Ich stehe um 7 Uhr auf. (I get up at 7 o’clock.)\neinkaufen: Wir kaufen samstags ein. (We go shopping on Saturdays.)\nmitkommen: Kommst du mit? (Are you coming along?)\nanfangen: Der Film fängt um 8 Uhr an. (The film starts at 8 o’clock.)\nausgehen: Ich gehe heute Abend aus. (I’m going out tonight.)");
        s4_2_2_1.setExplanation("Practical examples of the 6 essential separable verbs highlighted in Module IV.");
        s4_2_2_1.setVocabulary("fernsehen (to watch TV) | aufstehen (to get up) | einkaufen (to shop) | mitkommen (to come along) | anfangen (to start) | ausgehen (to go out)");
        s4_2_2_1.setGrammarRule("In yes/no questions, the conjugated base verb starts in Position 1, and the prefix stays at the end:\n• `Kommst du mit?` (Verb Pos 1, prefix at end).");
        s4_2_2_1.setExamples("Ich sehe abends fern. (I watch TV in the evenings.)\nWir kaufen samstags ein. (We go shopping on Saturdays.)\nDer Film fängt um 8 Uhr an. (The film starts at 8 o'clock.)\nIch gehe heute Abend aus. (I'm going out tonight.)");
        s4_2_2_1.setImportantNote("Notice 'Wir kaufen samstags ein' (We go shopping on Saturdays) — 'ein' is placed at the very end of the sentence.");
        lesson4_2_2.addSlide(s4_2_2_1);
        topic4_2.addLesson(lesson4_2_2);
        mod4.addTopic(topic4_2);

        // ==========================================
        // TOPIC 4.3: Der Imperativ
        // ==========================================
        Topic topic4_3 = new Topic(
                "Der Imperativ (The Imperative Mood / Commands)",
                "Der Imperativ",
                "Befehle, Bitten und Anweisungen in den Formen du, ihr und Sie.",
                3,
                mod4
        );

        // Lesson 4.3.1: Imperativ-Formen: du, ihr & Sie
        Lesson lesson4_3_1 = new Lesson(
                "Imperativ-Formen: du, ihr & Sie (Imperative Forms & Rules)",
                "Imperativ-Formen: du, ihr & Sie",
                "Die Bildung des Imperativs für die drei Anredeformen.",
                1,
                10,
                topic4_3
        );

        Slide s4_3_1_1 = new Slide("Die drei Imperativ-Formen (The Three Imperative Forms)", 1, "TABLE", lesson4_3_1);
        s4_3_1_1.setGermanText("Form | Regel / Rule | Beispiel (kommen) | English\n" +
                "du | Verbstamm — drop „-st“ and the pronoun „du“ | Komm! | Come! (informal singular)\n" +
                "ihr | Verb + t — drop the pronoun „ihr“ | Kommt! | Come! (informal plural)\n" +
                "Sie | Verb (Infinitiv) + Sie — formal | Kommen Sie! | Come! (formal singular & plural)\n\n" +
                "Unregelmäßiges Verb „sein“ (to be):\n" +
                "• du: sei!\n" +
                "• ihr: seid!\n" +
                "• Sie: seien Sie!");
        s4_3_1_1.setEnglishTranslation("du: verb stem — drop “-st” and the pronoun “du” -> Komm! (Come!)\nihr: verb + t — drop the pronoun “ihr” -> Kommt! (Come!)\nSie: verb (infinitive form) + Sie — formal -> Kommen Sie! (Come!)\nIrregular verb “sein” (to be): sei! (du) · seid! (ihr) · seien Sie! (Sie)");
        s4_3_1_1.setExplanation("The imperative mood (Der Imperativ) is used to give commands, instructions, advice, and requests. German has three imperative forms depending on who you are speaking to.");
        s4_3_1_1.setVocabulary("der Imperativ (imperative) | der Verbstamm (verb stem) | die Anweisung (instruction) | die Bitte (request) | der Befehl (command)");
        s4_3_1_1.setGrammarRule("Formation Rules:\n1. du: Take verb stem, omit '-st', drop pronoun 'du' (kommen -> Komm!).\n2. ihr: Take verb + 't', drop pronoun 'ihr' (kommen -> Kommt!).\n3. Sie: Infinitive + 'Sie' (kommen -> Kommen Sie!).\n4. sein: sei! / seid! / seien Sie!");
        s4_3_1_1.setExamples("Komm bitte hierher! (Come here please! - du)\nKommt alle mit! (Come along everyone! - ihr)\nKommen Sie bitte herein! (Please come in! - Sie)");
        s4_3_1_1.setImportantNote("In the du-form, never include the pronoun 'du' (say 'Komm!', not 'Komm du!').");
        lesson4_3_1.addSlide(s4_3_1_1);
        topic4_3.addLesson(lesson4_3_1);

        // Lesson 4.3.2: Imperativ-Beispiele im Alltag
        Lesson lesson4_3_2 = new Lesson(
                "Imperativ-Beispiele im Alltag (Everyday Imperative Examples)",
                "Imperativ-Beispiele im Alltag",
                "Wichtige Imperativsätze mit trennbaren Verben und unregelmäßigen Formen.",
                2,
                8,
                topic4_3
        );

        Slide s4_3_2_1 = new Slide("Wichtige Imperativ-Sätze (Key Imperative Examples)", 1, "EXAMPLE", lesson4_3_2);
        s4_3_2_1.setGermanText("Satz (Deutsch) | English | Anmerkung / Note\n" +
                "Mach die Tür zu! | Close the door! | trennbares Verb — Präfix „zu“ ans Satzende\n" +
                "Sei pünktlich! | Be on time! | unregelmäßig (sein)\n" +
                "Geht ins Kino! | Go to the cinema! | ihr — informeller Plural\n" +
                "Nehmen Sie Platz! | Please take a seat! | Sie — formell\n" +
                "Hab keine Angst! | Don’t be afraid! | unregelmäßig (haben)\n" +
                "Steh bitte auf! | Please stand up! | trennbares Verb (aufstehen)");
        s4_3_2_1.setEnglishTranslation("Mach die Tür zu!: Close the door! (separable verb — prefix “zu” to the end)\nSei pünktlich!: Be on time! (irregular sein)\nGeht ins Kino!: Go to the cinema! (ihr — informal plural)\nNehmen Sie Platz!: Please take a seat! (Sie — formal)\nHab keine Angst!: Don’t be afraid! (irregular haben)\nSteh bitte auf!: Please stand up! (separable verb)");
        s4_2_1_1.setExplanation("Practical imperative phrases for everyday communication, invitations, and requests.");
        s4_3_2_1.setVocabulary("die Tür (door) | zumachen (to close) | pünktlich (punctual) | ins Kino (to the cinema) | Platz nehmen (to take a seat) | die Angst (fear) | aufstehen (to stand up)");
        s4_3_2_1.setGrammarRule("Imperative with Separable Verbs:\n• The conjugated stem begins the sentence (Position 1).\n• The prefix is placed at the very end of the sentence.\n• Examples: 'Mach die Tür zu!', 'Steh bitte auf!'");
        s4_3_2_1.setExamples("Mach die Tür zu! (Close the door!)\nNehmen Sie Platz! (Please take a seat!)\nSei pünktlich! (Be on time!)");
        s4_3_2_1.setImportantNote("'Nehmen Sie Platz!' is the polite formal idiom for 'Please take a seat!' / 'Please sit down!'.");
        lesson4_3_2.addSlide(s4_3_2_1);
        topic4_3.addLesson(lesson4_3_2);
        mod4.addTopic(topic4_3);

        // ==========================================
        // TOPIC 4.4: Modalverben
        // ==========================================
        Topic topic4_4 = new Topic(
                "Modalverben (Modal Verbs: Conjugation & Word Order)",
                "Modalverben",
                "Konjugation von können, müssen, wollen, sollen, dürfen, mögen/möchte und Satzstellung.",
                4,
                mod4
        );

        // Lesson 4.4.1: Konjugation der Modalverben
        Lesson lesson4_4_1 = new Lesson(
                "Konjugation der Modalverben (Modal Verbs Conjugation)",
                "Konjugation der Modalverben",
                "Die Konjugation der 6 Modalverben für ich, du und er/sie/es.",
                1,
                10,
                topic4_4
        );

        Slide s4_4_1_1 = new Slide("Modalverben-Konjugationstabelle (Modal Verbs Table)", 1, "TABLE", lesson4_4_1);
        s4_4_1_1.setGermanText("Modalverb | Bedeutung / Meaning | ich | du | er / sie / es\n" +
                "können | can / be able to | kann | kannst | kann\n" +
                "müssen | must / have to | muss | musst | muss\n" +
                "wollen | to want to | will | willst | will\n" +
                "sollen | should | soll | sollst | soll\n" +
                "dürfen | may / be allowed to | darf | darfst | darf\n" +
                "mögen (möchte) | to like / would like | mag (möchte) | magst (möchtest) | mag (möchte)");
        s4_4_1_1.setEnglishTranslation("können: can / be able to -> ich kann, du kannst, er/sie/es kann\nmüssen: must / have to -> ich muss, du musst, er/sie/es muss\nwollen: to want to -> ich will, du willst, er/sie/es will\nsollen: should -> ich soll, du sollst, er/sie/es soll\ndürfen: may / be allowed to -> ich darf, du darfst, er/sie/es darf\nmögen (möchte): to like / would like -> ich mag (möchte), du magst (möchtest), er/sie/es mag (möchte)");
        s4_4_1_1.setExplanation("Modal verbs modify the main action by expressing ability, necessity, desire, duty, permission, or polite wishes.");
        s4_4_1_1.setVocabulary("können (can) | müssen (must) | wollen (to want) | sollen (should) | dürfen (may/allowed) | mögen (to like) | möchte (would like)");
        s4_4_1_1.setGrammarRule("Modal Verb Rules:\n1. 'ich' and 'er/sie/es' forms are ALWAYS identical with no ending suffix (-e or -t).\n2. Singular stems undergo vowel change (können -> kann, müssen -> muss, wollen -> will, dürfen -> darf, mögen -> mag).\n3. 'möchte' is the polite form of mögen meaning 'would like'.");
        s4_4_1_1.setExamples("ich will / er will (I want / he wants)\nich muss / er muss (I must / he must)\nich darf / er darf (I may / he may)\nich möchte / er möchte (I would like / he would like)");
        s4_4_1_1.setImportantNote("Notice 'er will' and 'er muss' have NO '-t' ending!");
        lesson4_4_1.addSlide(s4_4_1_1);
        topic4_4.addLesson(lesson4_4_1);

        // Lesson 4.4.2: Modalverben im Satz
        Lesson lesson4_4_2 = new Lesson(
                "Modalverben im Satz (Modal Verbs in Sentences)",
                "Modalverben im Satz",
                "Satzstellung mit Modalverben und Beispielsätze aus dem Modul.",
                2,
                10,
                topic4_4
        );

        Slide s4_4_2_1 = new Slide("Beispielsätze mit Modalverben (Modal Verbs Examples)", 1, "EXAMPLE", lesson4_4_2);
        s4_4_2_1.setGermanText("Satz (Deutsch) | English | Anmerkung / Note\n" +
                "Ich kann gut schwimmen. | I can swim well. | Modalverb + Infinitiv am Satzende\n" +
                "Wir müssen früh aufstehen. | We have to get up early. | trennbarer Infinitiv bleibt zusammen\n" +
                "Sie will nach Deutschland reisen. | She wants to travel to Germany. | Absicht / Wunsch\n" +
                "Ihr sollt die Hausaufgaben machen. | You should do your homework. | Pflicht / Empfehlung\n" +
                "Darf ich hier fotografieren? | May I take photos here? | Frageform (Position 1)\n" +
                "Ich möchte ein Eis essen. | I would like to eat ice cream. | möchte = höfliches „wollen“");
        s4_4_2_1.setEnglishTranslation("Ich kann gut schwimmen.: I can swim well. (modal + infinitive at the end)\nWir müssen früh aufstehen.: We have to get up early. (separable infinitive stays whole)\nSie will nach Deutschland reisen.: She wants to travel to Germany.\nIhr sollt die Hausaufgaben machen.: You should do your homework.\nDarf ich hier fotografieren?: May I take photos here? (question form)\nIch möchte ein Eis essen.: I would like to eat ice cream. (möchte = polite want)");
        s4_4_2_1.setExplanation("In German sentences with a modal verb, the conjugated modal verb stands in Position 2, while the main verb in the infinitive is pushed to the very end of the sentence.");
        s4_4_2_1.setVocabulary("gut schwimmen (to swim well) | früh aufstehen (to get up early) | nach Deutschland reisen (to travel to Germany) | die Hausaufgaben machen (to do homework) | fotografieren (to take photos) | ein Eis essen (to eat ice cream)");
        s4_4_2_1.setGrammarRule("Modal Verb Word Order:\n`[Subject] + [MODALVERB (Pos 2)] + [Details] + [INFINITIVE (End)]`\nImportant: Separable verbs like 'aufstehen' do NOT split when used with modal verbs: `Wir müssen früh aufstehen.`");
        s4_4_2_1.setExamples("Ich kann gut schwimmen. (I can swim well.)\nWir müssen früh aufstehen. (We have to get up early.)\nSie will nach Deutschland reisen. (She wants to travel to Germany.)\nIch möchte ein Eis essen. (I would like to eat ice cream.)");
        s4_4_2_1.setImportantNote("Remember: When combined with a modal verb, separable infinitives stay joined together at the end of the clause!");
        lesson4_4_2.addSlide(s4_4_2_1);
        topic4_4.addLesson(lesson4_4_2);
        mod4.addTopic(topic4_4);

        // ==========================================
        // TOPIC 4.5: Reisen, Wetter, Feste & Feiertage
        // ==========================================
        Topic topic4_5 = new Topic(
                "Reisen, Wetter, Feste & Feiertage (Travel, Weather & Celebrations)",
                "Reisen, Wetter, Feste & Feiertage",
                "Reisevokabular, Wetterausdrücke, Festtage, Festtagsgrüße und Dialoge zur Reiseplanung.",
                5,
                mod4
        );

        // Lesson 4.5.1: Reisen & Urlaub
        Lesson lesson4_5_1 = new Lesson(
                "Reisen & Urlaub (Travel & Vacation Vocabulary)",
                "Reisen & Urlaub",
                "Wortschatz für Reiseplanung, Verkehrsmittel und Übernachtung.",
                1,
                8,
                topic4_5
        );

        Slide s4_5_1_1 = new Slide("Reisewortschatz & Beispielfragen (Travel Vocabulary & Sentences)", 1, "VOCABULARY", lesson4_5_1);
        s4_5_1_1.setGermanText("Reisewortschatz:\n" +
                "die Reise — the trip\n" +
                "reisen — to travel\n" +
                "der Urlaub — vacation\n" +
                "planen — to plan\n" +
                "buchen — to book\n" +
                "das Flugzeug — airplane\n" +
                "der Zug — train\n" +
                "der Koffer — suitcase\n\n" +
                "Beispielfragen & Sätze:\n" +
                "Wohin fährst du in den Ferien? — Where are you going during the holidays?\n" +
                "Ich möchte einen Flug buchen. — I would like to book a flight.\n" +
                "Wir übernachten in einem Hotel. — We’re staying overnight in a hotel.");
        s4_5_1_1.setEnglishTranslation("the trip\nto travel\nvacation\nto plan\nto book\nairplane\ntrain\nsuitcase\n\nWhere are you going during the holidays?\nI would like to book a flight.\nWe’re staying overnight in a hotel.");
        s4_5_1_1.setExplanation("Core vocabulary for planning trips, booking transportation, and staying in hotels.");
        s4_5_1_1.setVocabulary("die Reise (trip) | reisen (to travel) | der Urlaub (vacation) | planen (to plan) | buchen (to book) | das Flugzeug (airplane) | der Zug (train) | der Koffer (suitcase) | in den Ferien (during holidays) | übernachten (to stay overnight) | das Hotel (hotel)");
        s4_5_1_1.setGrammarRule("Preposition Patterns for Travel:\n• `nach + [Land / Stadt]` (nach Italien, nach Deutschland)\n• `in den Ferien` (Accusative plural)\n• `in einem Hotel` (Dative neuter: in + dem Hotel)");
        s4_5_1_1.setExamples("Wohin fährst du in den Ferien? (Where are you going during the holidays?)\nIch möchte einen Flug buchen. (I would like to book a flight.)\nWir übernachten in einem Hotel. (We're staying overnight in a hotel.)");
        s4_5_1_1.setImportantNote("'der Urlaub' is vacation from work/business, whereas 'die Ferien' refers to school/university holidays.");
        lesson4_5_1.addSlide(s4_5_1_1);
        topic4_5.addLesson(lesson4_5_1);

        // Lesson 4.5.2: Das Wetter
        Lesson lesson4_5_2 = new Lesson(
                "Das Wetter (Talking About the Weather)",
                "Das Wetter",
                "Wetterphänomene, Temperaturen und Redewendungen rund ums Wetter.",
                2,
                8,
                topic4_5
        );

        Slide s4_5_2_1 = new Slide("Wetterausdrücke (Weather Expressions)", 1, "TABLE", lesson4_5_2);
        s4_5_2_1.setGermanText("Deutsch | English\n" +
                "das Wetter | the weather\n" +
                "Die Sonne scheint. | The sun is shining.\n" +
                "Es regnet. | It’s raining.\n" +
                "Es schneit. | It’s snowing.\n" +
                "Es ist windig. | It’s windy.\n" +
                "Es ist warm. | It’s warm.\n" +
                "Es ist kalt. | It’s cold.\n" +
                "Der Himmel ist bewölkt. | The sky is cloudy.\n" +
                "Es ist heute sehr heiß. | It’s very hot today.\n" +
                "Wie ist das Wetter heute? | What’s the weather like today?");
        s4_5_2_1.setEnglishTranslation("das Wetter: the weather\nDie Sonne scheint.: The sun is shining.\nEs regnet.: It’s raining.\nEs schneit.: It’s snowing.\nEs ist windig.: It’s windy.\nEs ist warm.: It’s warm.\nEs ist kalt.: It’s cold.\nDer Himmel ist bewölkt.: The sky is cloudy.\nEs ist heute sehr heiß.: It’s very hot today.\nWie ist das Wetter heute?: What’s the weather like today?");
        s4_5_2_1.setExplanation("Expressions for discussing the weather in German using impersonal verbs and adjectives.");
        s4_5_2_1.setVocabulary("das Wetter (weather) | scheinen (to shine) | regnen (to rain) | schneien (to snow) | windig (windy) | warm (warm) | kalt (cold) | bewölkt (cloudy) | heiß (hot)");
        s4_5_2_1.setGrammarRule("Impersonal 'es': Most weather expressions in German use the impersonal subject 'es' (Es regnet, Es schneit, Es ist windig, Es ist kalt).");
        s4_5_2_1.setExamples("Wie ist das Wetter heute? — Die Sonne scheint und es ist warm. (What's the weather like today? — The sun is shining and it is warm.)\nEs ist heute sehr heiß. (It is very hot today.)");
        s4_5_2_1.setImportantNote("In German, 'Wie ist das Wetter?' translates to 'What is the weather like?' (literally 'How is the weather?').");
        lesson4_5_2.addSlide(s4_5_2_1);
        topic4_5.addLesson(lesson4_5_2);

        // Lesson 4.5.3: Feste & Feiertage
        Lesson lesson4_5_3 = new Lesson(
                "Feste & Feiertage (Holidays & Celebrations)",
                "Feste & Feiertage",
                "Wichtige Feiertage, Geburtstage und traditionelle Festtagsgrüße.",
                3,
                8,
                topic4_5
        );

        Slide s4_5_3_1 = new Slide("Feiertage & Festtagsgrüße (Holidays & Greetings)", 1, "VOCABULARY", lesson4_5_3);
        s4_5_3_1.setGermanText("Wortschatz (Vocabulary):\n" +
                "Weihnachten — Christmas\n" +
                "Ostern — Easter\n" +
                "der Geburtstag — birthday\n" +
                "Silvester — New Year’s Eve\n" +
                "das Neujahr — New Year\n" +
                "die Ferien — holidays / school break\n" +
                "der Urlaub — vacation (from work)\n" +
                "feiern — to celebrate\n\n" +
                "Festtagsgrüße & Phrasen (Greetings & Phrases):\n" +
                "Frohe Weihnachten! → Merry Christmas!\n" +
                "Frohes neues Jahr! → Happy New Year!\n" +
                "Herzlichen Glückwunsch zum Geburtstag! → Happy Birthday!\n" +
                "Frohe Ostern! → Happy Easter!\n" +
                "Wir feiern zusammen mit der Familie. → We celebrate together with the family.");
        s4_5_3_1.setEnglishTranslation("Weihnachten (Christmas) | Ostern (Easter) | der Geburtstag (birthday) | Silvester (New Year’s Eve) | das Neujahr (New Year) | die Ferien (holidays / school break) | der Urlaub (vacation) | feiern (to celebrate)\n\nGreetings:\nFrohe Weihnachten! (Merry Christmas!)\nFrohes neues Jahr! (Happy New Year!)\nHerzlichen Glückwunsch zum Geburtstag! (Happy Birthday!)\nFrohe Ostern! (Happy Easter!)\nWir feiern zusammen mit der Familie. (We celebrate together with the family.)");
        s4_5_3_1.setExplanation("Major cultural celebrations in German-speaking countries and the traditional greetings used for each occasion.");
        s4_5_3_1.setVocabulary("Weihnachten (Christmas) | Ostern (Easter) | der Geburtstag (birthday) | Silvester (New Year's Eve) | das Neujahr (New Year) | feiern (to celebrate) | zusammen mit (together with) | die Familie (family)");
        s4_5_3_1.setGrammarRule("Congratulatory Preposition Pattern:\n'Herzlichen Glückwunsch zu + dem (zum) Geburtstag!' (Dative masculine: zu + dem).");
        s4_5_3_1.setExamples("Herzlichen Glückwunsch zum Geburtstag! (Happy Birthday!)\nWir feiern zusammen mit der Familie. (We celebrate together with the family.)\nFrohe Weihnachten und ein frohes neues Jahr! (Merry Christmas and a Happy New Year!)");
        s4_5_3_1.setImportantNote("In Germany, December 31st is celebrated as 'Silvester' (New Year's Eve), named after Pope Silvester I.");
        lesson4_5_3.addSlide(s4_5_3_1);
        topic4_5.addLesson(lesson4_5_3);

        // Lesson 4.5.4: Dialog: Eine Reise planen
        Lesson lesson4_5_4 = new Lesson(
                "Dialog: Eine Reise planen (Sample Trip Planning Dialogue)",
                "Dialog: Eine Reise planen",
                "Praktischer Dialog mit Urlaubsplänen, Wetter, Kofferpacken und Flugbuchung.",
                4,
                8,
                topic4_5
        );

        Slide s4_5_4_1 = new Slide("Urlaubsplanung im Dialog (Trip Planning Dialogue)", 1, "DIALOGUE", lesson4_5_4);
        s4_5_4_1.setGermanText("A: Was sind deine Pläne für die Ferien?\n" +
                "   (What are your plans for the holidays?)\n\n" +
                "B: Ich möchte nach Italien reisen. Wie ist das Wetter dort?\n" +
                "   (I’d like to travel to Italy. What’s the weather like there?)\n\n" +
                "A: Im Sommer ist es meistens sonnig und warm.\n" +
                "   (In summer it’s mostly sunny and warm.)\n\n" +
                "B: Toll! Ich muss noch meinen Koffer packen und einen Flug buchen.\n" +
                "   (Great! I still have to pack my suitcase and book a flight.)\n\n" +
                "A: Vergiss nicht, früh aufzustehen – dein Flug geht um 6 Uhr ab!\n" +
                "   (Don’t forget to get up early — your flight leaves at 6 o’clock!)");
        s4_5_4_1.setEnglishTranslation("A: What are your plans for the holidays?\nB: I’d like to travel to Italy. What’s the weather like there?\nA: In summer it’s mostly sunny and warm.\nB: Great! I still have to pack my suitcase and book a flight.\nA: Don’t forget to get up early — your flight leaves at 6 o’clock!");
        s4_5_4_1.setExplanation("A realistic travel planning conversation integrating modal verbs (möchte, muss), weather expressions (sonnig und warm), separable verbs (aufstehen, abgehen), and the imperative (Vergiss nicht!).");
        s4_5_4_1.setVocabulary("die Pläne (plans) | nach Italien reisen (to travel to Italy) | meistens sonnig (mostly sunny) | den Koffer packen (to pack the suitcase) | einen Flug buchen (to book a flight) | vergessen (to forget) | früh aufstehen (to get up early) | abgehen (to depart/leave)");
        s4_5_4_1.setGrammarRule("Key Grammar Points in Dialogue:\n1. Modal verbs: 'Ich möchte nach Italien reisen', 'Ich muss noch meinen Koffer packen'\n2. Imperative: 'Vergiss nicht' (du-form of vergessen)\n3. Separable verb with modal: 'früh aufzustehen', 'Flug geht ab'");
        s4_5_4_1.setExamples("A: Was sind deine Pläne für die Ferien?\nB: Ich möchte nach Italien reisen.\nA: Vergiss nicht, früh aufzustehen!");
        s4_5_4_1.setImportantNote("Notice 'Vergiss nicht' has a vowel change from 'e' to 'i' in the du-imperative of the irregular verb 'vergessen'.");
        lesson4_5_4.addSlide(s4_5_4_1);
        topic4_5.addLesson(lesson4_5_4);
        mod4.addTopic(topic4_5);

        // ==========================================
        // TOPIC 4.6: Wiederholung & Grammatik-Übersicht
        // ==========================================
        Topic topic4_6 = new Topic(
                "Wiederholung & Grammatik-Übersicht (Review & Practice Drills)",
                "Wiederholung & Grammatik-Übersicht",
                "Zusammenfassung der wichtigsten Grammatikregeln und Übungssätze aus dem Modul.",
                6,
                mod4
        );

        // Lesson 4.6.1: Grammatik-Wiederholung
        Lesson lesson4_6_1 = new Lesson(
                "Grammatik-Wiederholung (Comprehensive Grammar Review)",
                "Grammatik-Wiederholung",
                "Zusammenfassung von trennbaren Verben, Imperativ und Modalverben.",
                1,
                10,
                topic4_6
        );

        Slide s4_6_1_1 = new Slide("Kernregeln im Überblick (Core Grammar Rules Summary)", 1, "THEORY", lesson4_6_1);
        s4_6_1_1.setGermanText("1. Trennbare Verben:\n" +
                "• Das Präfix trennt sich im Hauptsatz und steht am Satzende.\n" +
                "• Das konjugierte Verb steht auf Position 2.\n" +
                "• Beispiel: Wir kaufen samstags ein.\n\n" +
                "2. Der Imperativ:\n" +
                "• du — Verbstamm: Komm!\n" +
                "• ihr — Verb + t: Kommt!\n" +
                "• Sie — Verb + Sie: Kommen Sie!\n" +
                "• Beispiel: Nehmen Sie Platz! (formell)\n\n" +
                "3. Modalverben:\n" +
                "• können (kann), müssen (muss), wollen (will), sollen (soll), dürfen (darf), mögen/möchte (mag/möchte)\n" +
                "• Modalverb auf Position 2, Hauptverb im Infinitiv am Satzende.\n\n" +
                "4. Reisen, Wetter & Feste:\n" +
                "• Wohin fährst du in den Ferien? | Wie ist das Wetter? | Frohe Weihnachten!");
        s4_6_1_1.setEnglishTranslation("1. Separable Verbs: Prefix separates and moves to end of main clause; conjugated verb in pos 2. Ex: Wir kaufen samstags ein.\n2. Imperative: du -> stem (Komm!), ihr -> verb+t (Kommt!), Sie -> verb+Sie (Kommen Sie!). Ex: Nehmen Sie Platz!\n3. Modal Verbs: können, müssen, wollen, sollen, dürfen, mögen/möchte. Modal verb in pos 2, main verb as infinitive at end.\n4. Travel, Weather & Holidays: Where are you going during the holidays? | What's the weather like? | Merry Christmas!");
        s4_6_1_1.setExplanation("A high-level synthesis of the fundamental grammar patterns taught across Module IV.");
        s4_6_1_1.setVocabulary("die Wiederholung (review) | das Präfix (prefix) | der Imperativ (imperative) | das Modalverb (modal verb)");
        s4_6_1_1.setGrammarRule("Key Rules Checklist:\n✓ Separable prefix jumps to sentence end\n✓ Imperative du-form drops -st and pronoun\n✓ Modal verbs have identical ich/er forms\n✓ Main verb infinitive goes to sentence end");
        s4_6_1_1.setExamples("Wir kaufen samstags ein. (We shop on Saturdays.)\nNehmen Sie Platz! (Please take a seat!)\nSie will nach Deutschland reisen. (She wants to travel to Germany.)\nDie Sonne scheint. (The sun is shining.)");
        s4_6_1_1.setImportantNote("Keep these 4 foundational grammar rules in mind for conversational and written German!");
        lesson4_6_1.addSlide(s4_6_1_1);
        topic4_6.addLesson(lesson4_6_1);

        // Lesson 4.6.2: Wiederholungs- und Übungssätze
        Lesson lesson4_6_2 = new Lesson(
                "Wiederholungs- und Übungssätze (Practice & Review Sentences)",
                "Wiederholungs- und Übungssätze",
                "Selbstkontrolle mit den originalen Übungssätzen aus dem Modul.",
                2,
                10,
                topic4_6
        );

        Slide s4_6_2_1 = new Slide("Übungssätze aus dem Modul (Module Practice Sentences)", 1, "EXAMPLE", lesson4_6_2);
        s4_6_2_1.setGermanText("Übungen zur Selbstkontrolle (Practice with Solutions):\n\n" +
                "1. Ich stehe um 7 Uhr auf. (Infinitiv: aufstehen — trennbares Verb)\n" +
                "   [Lösung: stehe ... auf]\n\n" +
                "2. Komm mit! (du-Imperativ von mitkommen)\n" +
                "   [Lösung: Komm mit!]\n\n" +
                "3. Wir müssen unsere Hausaufgaben machen. (Modalverb: müssen)\n" +
                "   [Lösung: müssen]\n\n" +
                "4. Wie ist das Wetter? Es regnet / Es schneit / Die Sonne scheint.\n" +
                "   [Lösung: regnet / schneit / scheint]\n\n" +
                "5. Übersetzung: „Ich möchte nach Deutschland reisen.“\n" +
                "   (English: “I would like to travel to Germany.”)");
        s4_6_2_1.setEnglishTranslation("1. Ich stehe um 7 Uhr auf. (I get up at 7 o'clock. - separable verb aufstehen)\n2. Komm mit! (Come along! - du-imperative of mitkommen)\n3. Wir müssen unsere Hausaufgaben machen. (We have to do our homework. - modal verb müssen)\n4. Wie ist das Wetter? Es regnet. (What's the weather like? It's raining.)\n5. Translation: “I would like to travel to Germany.” -> „Ich möchte nach Deutschland reisen.“");
        s4_6_2_1.setExplanation("Self-assessment exercise based directly on page 18 of the course syllabus. Check your answers against the provided solutions.");
        s4_6_2_1.setVocabulary("die Hausaufgaben machen (to do homework) | mitkommen (to come along) | nach Deutschland reisen (to travel to Germany)");
        s4_6_2_1.setGrammarRule("Review Notes on Solutions:\n1. 'aufstehen' splits into 'stehe ... auf'\n2. du-Imperativ of 'mitkommen' is 'Komm mit!'\n3. 'wir müssen' conjugates with regular plural ending\n4. Impersonal 'es' with weather verbs\n5. 'möchte' + infinitive 'reisen' at end of clause");
        s4_6_2_1.setExamples("1. Ich stehe um 7 Uhr auf.\n2. Komm mit!\n3. Wir müssen unsere Hausaufgaben machen.\n4. Es regnet.\n5. Ich möchte nach Deutschland reisen.");
        s4_6_2_1.setImportantNote("Viel Erfolg! Alles Gute für die Prüfung und danke schön für Ihren Fleiß!");
        lesson4_6_2.addSlide(s4_6_2_1);
        topic4_6.addLesson(lesson4_6_2);
        mod4.addTopic(topic4_6);

        moduleRepository.save(mod4);
    }

    private void initQuestions() {
        Module mod1 = moduleRepository.findByCode("MODULE_1").orElse(null);
        Module mod2 = moduleRepository.findByCode("MODULE_2").orElse(null);
        Module mod3 = moduleRepository.findByCode("MODULE_3").orElse(null);
        Module mod4 = moduleRepository.findByCode("MODULE_4").orElse(null);

        questionBankInitializer.seedAllTopicQuestions(mod1, mod2, mod3, mod4, topicRepository, quizRepository, questionRepository);
    }

    private void initQuizzes() {
        List<Topic> allTopics = topicRepository.findAll();
        for (Topic topic : allTopics) {
            List<Question> topicQuestions = questionRepository.findByTopicId(topic.getId());

            if (!topicQuestions.isEmpty()) {
                List<Quiz> existingQuizzes = quizRepository.findByTopicId(topic.getId());
                Quiz quiz;
                if (existingQuizzes.isEmpty()) {
                    quiz = new Quiz("Quiz: " + topic.getTitle(), "Topic Quiz testing comprehension of " + topic.getTitle(), 70, topic);
                    quiz.setTimeLimitMinutes(15);
                    quiz = quizRepository.save(quiz);
                } else {
                    quiz = existingQuizzes.get(0);
                }

                for (Question q : topicQuestions) {
                    if (q.getQuiz() == null) {
                        q.setQuiz(quiz);
                        questionRepository.save(q);
                    }
                }
            }
        }
    }

    private void initModuleTests() {
        List<Module> allModules = moduleRepository.findAllByOrderByOrderIndexAsc();
        for (Module module : allModules) {
            List<Test> existingTests = testRepository.findByModuleId(module.getId());
            Test test;
            if (existingTests.isEmpty()) {
                String title = module.getTitle() + " - Comprehensive Test";
                String description = "Comprehensive final assessment covering all topics, grammar structures, vocabulary, and dialogue in " + module.getTitle() + ".";
                test = new Test(title, description, 75, module);
                test.setTimeLimitMinutes(30);
                test = testRepository.save(test);
            } else {
                test = existingTests.get(0);
            }

            // Associate module questions to module test
            List<Question> moduleQuestions = questionRepository.findByModuleId(module.getId());
            for (Question q : moduleQuestions) {
                if (q.getTest() == null) {
                    q.setTest(test);
                    questionRepository.save(q);
                }
            }
        }
    }
}
