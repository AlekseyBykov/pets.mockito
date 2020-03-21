package alekseybykov.portfolio.mockito.services;

import alekseybykov.portfolio.mockito.dependencies.external.DocumentService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WhitePaperServiceTest {

	private static List<String> whitePapers;
	private static ArgumentCaptor<String> argumentCaptor;

	@BeforeClass
	public static void setup() {
		whitePapers = Arrays.asList("Document1 rev.01", "Document2 rev.02", "Document1 rev.02", "Document4 rev.03");
		argumentCaptor = ArgumentCaptor.forClass(String.class);
	}

	@Test
	public void testFilterWhitePapers() {
		DocumentService documentService = mock(DocumentService.class);
		String whitePaperName = "Document1";

		when(documentService.searchDocumentsByName(whitePaperName)).thenReturn(whitePapers);

		WhitePaperService whitePaperService = new WhitePaperService(documentService);
		List<String> whitePapers = whitePaperService.filterWhitePapers(whitePaperName);

		assertEquals(2, whitePapers.size());
	}

	@Test
	public void testDeleteWhitePapers() {
		DocumentService documentService = mock(DocumentService.class);

		String whitePaperName = "Document1";

		when(documentService.searchDocumentsByName(whitePaperName)).thenReturn(whitePapers);

		WhitePaperService whitePaperService = new WhitePaperService(documentService);

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
		assertThat(argumentCaptor.getAllValues().size(), is(2));
	}

	@Test
	public void testFilterWhitePapersUsingBddStyle() {
		DocumentService documentService = mock(DocumentService.class);
		WhitePaperService whitePaperService = new WhitePaperService(documentService);
		
		String whitePaperName = "Document2";

		//given
		given(documentService.searchDocumentsByName(whitePaperName)).willReturn(whitePapers);

		//when
		List<String> whitePapers = whitePaperService.filterWhitePapers(whitePaperName);

		//then
		assertThat(whitePapers.size(), is(1));
	}
}
