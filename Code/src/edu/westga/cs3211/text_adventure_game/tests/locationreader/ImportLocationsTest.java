package edu.westga.cs3211.text_adventure_game.tests.locationreader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.LocationReader;

/**
 * The test class for the LocationReader importLocations method.
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class ImportLocationsTest {
	private static final String TEST_FILE_PATH = "test_locations.csv";
	private LocationReader locationReader;

	@AfterEach
	private void deleteTestFile() {
		File file = new File(TEST_FILE_PATH);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * Tests the importLocations method.
	 *
	 * @throws IOException when an I/O exception has occurred.
	 */
	@Test
	public void testImportLocations() throws IOException {
		this.createTestFile("ATTIC,Description1,NONE,true,NONE\n" + "BASEMENT,Description2,NONE,false,NONE\n");

		this.locationReader = new LocationReader(TEST_FILE_PATH);
		ArrayList<Location> locations = this.locationReader.importLocations();

		assertAll(() -> assertEquals(2, locations.size()), () -> assertEquals(GlobalEnums.LocationName.ATTIC, locations.get(0).getName()),
				() -> assertEquals(GlobalEnums.LocationName.BASEMENT, locations.get(1).getName()));
	}

	/**
	 * Tests the importLocations method for an invalid line.
	 *
	 * @throws IOException when an I/O exception has occurred.
	 */
	@Test
	public void testImportLocationsInvalidLine() throws IOException {
		this.createTestFile("InvalidLocation,InvalidDescription,INVALID_HAZARD,true\n");

		this.locationReader = new LocationReader(TEST_FILE_PATH);
		ArrayList<Location> locations = this.locationReader.importLocations();

		assertEquals(0, locations.size());
	}
	
	/**
	 * Tests the importLocations method for file not found.
	 *
	 * @throws IOException when an I/O exception has occurred.
	 */
	@Test
	public void testFileNotFound() throws IOException {
		this.locationReader = new LocationReader(TEST_FILE_PATH);
		ArrayList<Location> locations = this.locationReader.importLocations();

		assertEquals(0, locations.size());
	}

	private void createTestFile(String content) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
			writer.write(content);
		}
	}
}
