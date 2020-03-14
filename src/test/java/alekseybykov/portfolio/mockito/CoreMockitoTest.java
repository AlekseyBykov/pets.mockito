package alekseybykov.portfolio.mockito;

import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoreMockitoTest {

	@Test
	public void testSetAndCheckSizeOfMock() {
		Map mapMock = mock(Map.class);
		when(mapMock.size()).thenReturn(1);

		assertEquals(1, mapMock.size());
	}
}
