package alekseybykov.portfolio.mockito;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoreMockitoTest {

	@Test
	public void testMockSizeCall() {
		List<Integer> list = mockList();
		when(list.size()).thenReturn(1);

		assertEquals(1, list.size());
	}

	@Test
	public void testMockGetCall() {
		List<Integer> list = mockList();
		when(list.get(0)).thenReturn(NumberUtils.INTEGER_ONE);

		assertEquals(NumberUtils.INTEGER_ONE, list.get(0));
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> mockList() {
		return mock(List.class);
	}
}
