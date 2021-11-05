package com.creditsuisse.assignment;
import com.creditsuisse.assignment.AssignmentApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AssignmentApplication.class)
public class BaseIntegrationTest {
}
