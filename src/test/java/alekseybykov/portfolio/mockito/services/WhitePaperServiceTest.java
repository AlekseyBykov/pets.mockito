package alekseybykov.portfolio.mockito.services;

import alekseybykov.portfolio.mockito.dependencies.external.DocumentService;
import alekseybykov.portfolio.mockito.utils.Marker;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*@RunWith(MockitoJUnitRunner.class)*/
@RunWith(PowerMockRunner.class)
@PrepareForTest({Marker.class})
public class WhitePaperServiceTest {

	@Mock
	private DocumentService documentService;
	@Captor
	private ArgumentCaptor<String> argumentCaptor;
	@InjectMocks
	private WhitePaperService whitePaperService;

	private static List<String> whitePapers;
	/*private static ArgumentCaptor<String> argumentCaptor;*/

	@BeforeClass
	public static void setup() {
		whitePapers = Arrays.asList("Document1 rev.01", "Document2 rev.02", "Document1 rev.02", "Document4 rev.03");
		/*argumentCaptor = ArgumentCaptor.forClass(String.class);*/
	}

	@Test
	public void testFilterWhitePapers() {

		/*DocumentService documentService = mock(DocumentService.class);*/
		String whitePaperName = "Document1";

		when(documentService.searchDocumentsByName(whitePaperName)).thenReturn(whitePapers);

		/*WhitePaperService whitePaperService = new WhitePaperService(documentService);*/
		List<String> whitePapers = whitePaperService.filterWhitePapers(whitePaperName);

		assertEquals(2, whitePapers.size());
	}

	@Test
	public void testDeleteWhitePapers() {
		/*DocumentService documentService = mock(DocumentService.class);*/

		String whitePaperName = "Document1";

		when(documentService.searchDocumentsByName(whitePaperName)).thenReturn(whitePapers);

		/*WhitePaperService whitePaperService = new WhitePaperService(documentService);*/

		whitePaperService.deleteWhitePapers(whitePaperName);

		verify(documentService).deleteDocumentByName("Document1 rev.01");
		verify(documentService).deleteDocumentByName("Document1 rev.02");

		verify(documentService, times(1)).deleteDocumentByName("Document1 rev.01");
		verify(documentService, times(1)).deleteDocumentByName("Document1 rev.02");

		verify(documentService, atLeastOnce()).deleteDocumentByName("Document1 rev.01");
		verify(documentService, atLeast(1)).deleteDocumentByName("Document1 rev.02");

		then(documentService).should().deleteDocumentByName("Document1 rev.01");
		then(documentService).should().deleteDocumentByName("Document1 rev.02");

		verify(documentService, never()).deleteDocumentByName("Document2 rev.02");
		verify(documentService, never()).deleteDocumentByName("Document4 rev.03");

		then(documentService).should(never()).deleteDocumentByName("Document2 rev.02");
		then(documentService).should(never()).deleteDocumentByName("Document4 rev.03");

		verify(documentService, times(2)).deleteDocumentByName(argumentCaptor.capture());

		List<String> arguments = argumentCaptor.getAllValues();

		assertThat(arguments.size(), is(2));
		assertThat(arguments, hasSize(2));
		assertThat(arguments, everyItem(not(StringUtils.EMPTY)));
	}

	@Test
	public void testFilterWhitePapersUsingBddStyle() {
		/*DocumentService documentService = mock(DocumentService.class);*/
		/*WhitePaperService whitePaperService = new WhitePaperService(documentService);*/
		
		String whitePaperName = "Document2";

		//given
		given(documentService.searchDocumentsByName(whitePaperName)).willReturn(whitePapers);

		//when
		List<String> whitePapers = whitePaperService.filterWhitePapers(whitePaperName);

		//then
		assertThat(whitePapers.size(), is(1));
	}

	@Test
	public void testMarkWhitePaper() {
		String whitePaperName = "Document2";
		assertEquals(whitePaperService.markWhitePaper(whitePaperName), "Document2#MARKED");

		PowerMockito.mockStatic(Marker.class);
		when(Marker.getMark()).thenReturn("MOCKED");

		assertEquals(whitePaperService.markWhitePaper(whitePaperName), "Document2#MOCKED");

		// Checking that this method was called
		PowerMockito.verifyStatic();
		Marker.getMark();
		PowerMockito.verifyStatic(times(1));
	}
}
