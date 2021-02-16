import com.lesfurets.jenkins.unit.declarative.*
import org.junit.Test
import org.junit.Before

class MavenTest extends DeclarativePipelineTest {

        @Override
        @Before
        void setUp() throws Exception {
            baseScriptRoot = 'jenkinssharedlib'
            scriptRoots += 'src/org/company/jsl'
            scriptExtension = 'groovy'
            super.setUp()
        }

        @Test
        void should_execute_without_errors() throws Exception {
            def script = runScript("test/org/company/jsl/Jenkinsfile")
            assertJobStatusSuccess()
            printCallStack()
        }
}