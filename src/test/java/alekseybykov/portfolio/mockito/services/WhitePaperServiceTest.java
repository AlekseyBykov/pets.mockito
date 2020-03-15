package alekseybykov.portfolio.mockito.services;

import alekseybykov.portfolio.mockito.dependencies.external.DocumentService;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WhitePaperServiceTest {

	@Test
	public void testFilterWhitePapers() {
		DocumentService documentService = mock(DocumentService.class);
		List<String> documents = Arrays.asList("Document1 rev.01",
				"Document2 rev.02", "Document1 rev.02", "Document4 rev.03");

		String documentName = "Document1";
		when(documentService.searchDocuments(documentName)).thenReturn(documents);

		WhitePaperService whitePaperService = new WhitePaperService(documentService);
		List<String> whitePapers = whitePaperService.filterWhitePapers(documentName);

		assertEquals(2, whitePapers.size());
	}
}
