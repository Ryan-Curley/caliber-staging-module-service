package com.revature.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.backend.model.Associate;
import com.revature.backend.model.Manager;
import com.revature.backend.repository.BackendRepo;
import com.revature.backend.repository.ManagerRepository;
import com.revature.backend.service.ManagerService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ManagerTesting {

	@MockBean
	BackendRepo mockBackendRepo;

	@MockBean
	ManagerRepository mockManagerRepository;

	@Autowired
	ManagerService managerService;

	/**
	 * Tests that managerService behaves properly when the repo finds some manager(s)
	 */
	@Test
	void findAllManagers() {
		List<Manager> expected = new ArrayList<>();
		expected.add(new Manager());
		when(mockManagerRepository.findAllManagers()).thenReturn(expected);
		List<Manager> actual = managerService.getAllManagers();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	/**
	 * Tests that managerService behaves properly when the repo throws an exception
	 */
	@Test
	void findAllManagerFail() {
		when(mockManagerRepository.findAllManagers())
			.thenThrow(new ArrayIndexOutOfBoundsException()); // arbitrary type
		List<Manager> m = managerService.getAllManagers();
		assertNull(m);
	}

	/**
	 * Tests that managerService correctly builds the map of managers and the count of
	 * their assigned associates
	 *
	 * Important: relies on getAllManagers working
	 */
	@Test
	public void testGetAllManagersAndAssociates() {
		// prepare the data to be mapped
		Map<Manager, Integer> expectedMap = new HashMap<>();
		List<Manager> managerList = new ArrayList<>();

		Manager m1 = new Manager();
		m1.setId(1);
		int m1Count = 5;
		List<Associate> m1Assocs = buildAssocList(m1, m1Count);
		managerList.add(m1);
		expectedMap.put(m1, m1Count);

		Manager m2 = new Manager();
		m2.setId(2);
		int m2Count = 7;
		List<Associate> m2Assocs = buildAssocList(m2, m2Count);
		managerList.add(m2);
		expectedMap.put(m2, m2Count);

		Manager m3 = new Manager();
		m3.setId(3);
		int m3Count = 20;
		List<Associate> m3Assocs = buildAssocList(m3, m3Count);
		managerList.add(m3);
		expectedMap.put(m3, m3Count);

		when(mockManagerRepository.findAllManagers()).thenReturn(managerList);
		when(mockBackendRepo.findAssociatesByManagerId(1)).thenReturn(m1Assocs);
		when(mockBackendRepo.findAssociatesByManagerId(2)).thenReturn(m2Assocs);
		when(mockBackendRepo.findAssociatesByManagerId(3)).thenReturn(m3Assocs);

    Map<Manager, Integer> actualMap = managerService.getAllManagersAndAssociates();
		assertEquals(expectedMap, actualMap);
	}
	/**
	 * For future batches implement a test for
	*/
	@Test
	public void saveAllManagersSuccess() {
	}
	/**
	 * Helper method that builds a list of dummy associates, assigned to the given manager
	 */
	private List<Associate> buildAssocList(Manager manager, int numAssocs){
		List<Associate> m1Associates = new ArrayList<>();
		for (int i = 0; i < numAssocs; i++){
			Associate assoc = new Associate();
			assoc.setManager(manager);
			m1Associates.add(assoc); // should be ok to have null fields
		}
		return m1Associates;
	}
}
