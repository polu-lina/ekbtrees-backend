package ru.ekbtreeshelp.tests.api

import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

import static org.hamcrest.Matchers.*

class CommentControllerTest extends ApiTest {

    @BeforeEach
    void initContext() {
        String email = "${UUID.randomUUID()}@test.mail"
        TestUser user = new TestUser(
                email: email,
                password: 'volunteer'
        )

        createUser(user)

        testContext = new TestContext(
                user: user
        )
    }

    @Test
    void testAddComment() {
        addComment(createTree())
    }

    @Test
    void testGetCommentsByTreeId() {
        Long newTreeId = createTree()
        Long newCommentId = addComment(newTreeId)

        get("/api/comment/by-tree/${ newTreeId }")
                .then()
                .statusCode(200)
                .body("find { it.id == ${ newCommentId } }?.treeId as Long", equalTo(newTreeId))
    }

    @Test
    void testUpdateComment() {
        Long newTreeId = createTree()
        Long newCommentId = addComment(newTreeId)

        String newText = 'newText'
        put("/api/comment/${ newCommentId }", [text: newText])
                .then()
                .statusCode(200)

        get("/api/comment/by-tree/${ newTreeId }")
                .then()
                .statusCode(200)
                .body("find { it.id == ${ newCommentId } }?.text", equalTo(newText))
    }

    @Test
    void testDeleteComment() {
        Long newCommentId = addComment(createTree())

        delete("/api/comment/${ newCommentId }")
                .then()
                .statusCode(200)
    }

    private static Response sendAddCommentRequest(Long treeId) {
        Long newTreeId = treeId ?: createTree()

        return post('/api/comment', [text: 'comment', treeId: newTreeId])
    }

    private static Long addComment(Long treeId) {

        Response response = sendAddCommentRequest(treeId)

        response
                .then()
                .statusCode(201)

        return response.asString().toLong()
    }

}
