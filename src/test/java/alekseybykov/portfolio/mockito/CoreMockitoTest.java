package alekseybykov.portfolio.mockito;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Aleksey Bykov
 * @since 14.03.2020
 */
public class CoreMockitoTest {

	@Test
	public void testSizeCallUsingMock() {
		List<Integer> list = mockList();
		when(list.size()).thenReturn(1);

		assertEquals(1, list.size());
	}

	@Test
	public void testGetCallUsingMock() {
		List<Integer> list = mockList();
		when(list.get(0)).thenReturn(NumberUtils.INTEGER_ONE);

		assertEquals(NumberUtils.INTEGER_ONE, list.get(0));
	}

	@Test
	public void testGetByAnyIdxCallUsingMock() {
		List<Integer> list = mockList();
		Mockito.when(list.get(Mockito.anyInt())).thenReturn(NumberUtils.INTEGER_ZERO);

		assertEquals(NumberUtils.INTEGER_ZERO, list.get(0));
		assertEquals(NumberUtils.INTEGER_ZERO, list.get(1));
		assertEquals(NumberUtils.INTEGER_ZERO, list.get(100));
	}

	@Test
	public void testThreeGetCallsUsingMock() {
		List<Integer> list = mockList();
		when(list.size()).thenReturn(-1).thenReturn(0).thenReturn(1);

		assertEquals(-1, list.size());
		assertEquals(0, list.size());
		assertEquals(1, list.size());
	}

	@Test
	public void testGetCallWithUsingBddStyle() {
		List<Integer> list = mockList();

		given(list.get(Mockito.anyInt())).willReturn(NumberUtils.INTEGER_ZERO);

		assertThat(NumberUtils.INTEGER_ZERO, is(list.get(0)));
		assertThat(NumberUtils.INTEGER_ZERO, is(list.get(1)));
	}

	@Test(expected = RuntimeException.class)
	public void testGetCallWithExceptionThrowing() {
		List<Integer> list = mockList();

		when(list.size()).thenThrow(new RuntimeException());

		assertEquals(0, list.size());
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> mockList() {
		return mock(List.class);
	}
}
