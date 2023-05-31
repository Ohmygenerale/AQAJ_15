package exercise.article;

import exercise.worker.Worker;
import exercise.worker.WorkerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class WorkerImplTest {

    private Worker worker;

    @Mock
    private Library library;

    @BeforeEach
    public void SetUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        worker = new WorkerImpl(library);
    }

    @Test
    public void shouldAddNewArticle() throws Exception {
        List<Article> newArticles = new ArrayList<>();

        newArticles.add(new Article(
                "New Time",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми. Можно обладать хорошими знаниями и умениями, но без развитых soft skills очень трудно работать в современных компаниях. Особенно айтишникам. Рассказываем, почему.",
                "Елена Еленовна",
                LocalDate.of(2023, 5, 30)));

        worker.addNewArticles(newArticles);
        verify(library).store(2023, newArticles);
    }

    @Test
    public void getCatalogContainsNewArticles() throws Exception {
        List<Article> newArticles = new ArrayList<>();

        newArticles.add(new Article(
                "New Time",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми. Можно обладать хорошими знаниями и умениями, но без развитых soft skills очень трудно работать в современных компаниях. Особенно айтишникам. Рассказываем, почему.",
                "Елена Еленовна",
                LocalDate.of(2023, 5, 30)));

        worker.addNewArticles(newArticles);
        when(library.getAllTitles()).thenReturn(List.of("New Time"));
        assertTrue(worker.getCatalog().contains(newArticles.get(0).getTitle()));
    }

    @Test
    public void prepareArticlesNoData() throws Exception {
        List<Article> newArticles = new ArrayList<>();

        newArticles.add(new Article(
                "New Time",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми. Можно обладать хорошими знаниями и умениями, но без развитых soft skills очень трудно работать в современных компаниях. Особенно айтишникам. Рассказываем, почему.",
                "Елена Еленовна",
                LocalDate.of(2023, 5, 30)));
        newArticles.add(new Article(
                "New Time Null",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми. Можно обладать хорошими знаниями и умениями, но без развитых soft skills очень трудно работать в современных компаниях. Особенно айтишникам. Рассказываем, почему.",
                "Елена Еленовна",
                null));

        List<Article> articles = worker.prepareArticles(newArticles);
        assertEquals(articles.get(1).getCreationDate(), LocalDate.now());
    }
    @Test
    public void prepareArticlesNoTitle() throws Exception {
        List<Article> newArticles = new ArrayList<>();

        newArticles.add(new Article(
                null,
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми",
                "Елена Еленовна",
                LocalDate.of(2023, 5, 30)));
        newArticles.add(new Article(
                "New Time",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми. Можно обладать хорошими знаниями и умениями, но без развитых soft skills очень трудно работать в современных компаниях. Особенно айтишникам. Рассказываем, почему.",
                "Елена Еленовна",
                LocalDate.of(2023, 5, 31)));

        List<Article> articles = worker.prepareArticles(newArticles);
        assertEquals(1, articles.size());
        assertEquals("New Time", articles.get(0).getTitle());

    }
    @Test
    public void prepareArticlesNoContent() throws Exception {
        List<Article> newArticles = new ArrayList<>();

        newArticles.add(new Article(
                "New Time",
                null,
                "Елена Еленовна",
                LocalDate.of(2023, 5, 30)));
        newArticles.add(new Article(
                "New Time Null",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми. Можно обладать хорошими знаниями и умениями, но без развитых soft skills очень трудно работать в современных компаниях. Особенно айтишникам. Рассказываем, почему.",
                "Елена Еленовна",
                LocalDate.of(2023, 5, 31)));

        List<Article> articles = worker.prepareArticles(newArticles);
        assertEquals(1, articles.size());
        assertNotEquals("New Time", articles.get(0).getTitle());

    }
    @Test
    public void prepareArticlesNoAuthor() throws Exception {
        List<Article> newArticles = new ArrayList<>();

        newArticles.add(new Article(
                "New Time",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми",
                null,
                LocalDate.of(2023, 5, 30)));
        newArticles.add(new Article(
                "New Time Null",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми. Можно обладать хорошими знаниями и умениями, но без развитых soft skills очень трудно работать в современных компаниях. Особенно айтишникам. Рассказываем, почему.",
                "Елена Еленовна",
                LocalDate.of(2023, 5, 31)));

        List<Article> articles = worker.prepareArticles(newArticles);
        assertEquals(1, articles.size());
        assertEquals("Елена Еленовна", articles.get(0).getAuthor());
    }

    @Test
    public void prepareArticlesNotRepeatedValue() throws Exception {
        List<Article> newArticles = new ArrayList<>();

        newArticles.add(new Article(
                "New Time",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми. Можно обладать хорошими знаниями и умениями, но без развитых soft skills очень трудно работать в современных компаниях. Особенно айтишникам. Рассказываем, почему.",
                "Елена Еленовна",
                LocalDate.of(2023, 5, 30)));
        newArticles.add(new Article(
                "New Time",
                "Мягкие навыки помогают решать задачи и взаимодействовать с другими людьми. Можно обладать хорошими знаниями и умениями, но без развитых soft skills очень трудно работать в современных компаниях. Особенно айтишникам. Рассказываем, почему.",
                "Елена Еленовна",
                null));

        List<Article> articles = worker.prepareArticles(newArticles);
        assertEquals(1, articles.size());
    }

}