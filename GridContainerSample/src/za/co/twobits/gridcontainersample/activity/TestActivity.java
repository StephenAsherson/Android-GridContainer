/*
 * Copyright (c) 2013 Stephen Asherson
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package za.co.twobits.gridcontainersample.activity;

import java.util.ArrayList;
import java.util.List;

import za.co.twobits.gridcontainer.ui.GridContainer;
import za.co.twobits.gridcontainer.ui.GridContainer.GridRowDetails;
import za.co.twobits.gridcontainersample.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Simple test activity to demonstrate the GridContainer class.
 * @author Stephen Asherson
 */
public class TestActivity extends Activity
{
	private GridContainer gridContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);

		gridContainer = (GridContainer) findViewById(R.id.gridLayout);
		addItemsToGrid();
	}

	/**
	 * Add some test items to the grid
	 */
	private void addItemsToGrid()
	{
		int gridItemCount = 4;

		List<View> gridItems = new ArrayList<View>();

		for (int count = 0; count < gridItemCount; count++)
		{
			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			RelativeLayout gridBlockView = (RelativeLayout) vi.inflate(
				R.layout.grid_block_layout, gridContainer, false);

			// just assign our test images to the grid
			int imageRes = -1;
			switch (count)
			{
				case 0:
					imageRes = R.drawable.grid_image_one;
					break;
				case 1:
					imageRes = R.drawable.grid_image_two;
					break;
				case 2:
					imageRes = R.drawable.grid_image_three;
					break;
				case 3:
					imageRes = R.drawable.grid_image_four;
					break;
				default:
					break;
			}
			
			ImageView testImageView = (ImageView) gridBlockView
				.findViewById(R.id.grid_image);
			testImageView.setImageResource(imageRes);
			
			gridItems.add(gridBlockView);
		}

		// We have different configurations based on portrait and
		// landscape
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			gridContainer.addItemsToGrid(gridItems, rowConfigurationPortrait);
		}
		else
		{
			gridContainer.addItemsToGrid(gridItems, rowConfigurationLandscape);
		}
	}

	//
	// Constants
	//
	private static final int GRID_ITEM_MARGIN = 2; // A margin of 2 dp between the views in each row.

	//
	// The row configurations for the test grid.
	//
	private static final List<GridRowDetails> rowConfigurationPortrait;
	private static final List<GridRowDetails> rowConfigurationLandscape;

	static
	{
		// Just using hard-coded heights for this example
		rowConfigurationPortrait = new ArrayList<GridRowDetails>();
		rowConfigurationPortrait.add(new GridRowDetails(1, GRID_ITEM_MARGIN,
			300));
		rowConfigurationPortrait
			.add(new GridRowDetails(2, GRID_ITEM_MARGIN, 200));
		rowConfigurationPortrait.add(new GridRowDetails(1, GRID_ITEM_MARGIN,
			300));

		rowConfigurationLandscape = new ArrayList<GridRowDetails>();
		rowConfigurationLandscape.add(new GridRowDetails(3, GRID_ITEM_MARGIN,
			200));
		rowConfigurationLandscape.add(new GridRowDetails(1, GRID_ITEM_MARGIN,
			300));
	}

}
