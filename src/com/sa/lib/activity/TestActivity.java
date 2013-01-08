/*
 * Copyright 2013 Stephen Asherson
 * 
 * This file is part of GridContainer.
 * 
 * GridContainer is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * GridContainer is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * GridContainer. If not, see http://www.gnu.org/licenses/.
 */
package com.sa.lib.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sa.lib.gridcontainer.R;
import com.sa.lib.ui.GridContainer;
import com.sa.lib.ui.GridContainer.GridRowDetails;

/**
 * Simple test activity to demonstrate the GridContainer class. This project
 * is a library project so it cannot be launched... if you want to run this
 * activity it is best to just create a new project and run it from there.
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
				.findViewById(R.id.gridImage);
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
			180));
		rowConfigurationPortrait
			.add(new GridRowDetails(2, GRID_ITEM_MARGIN, 90));
		rowConfigurationPortrait.add(new GridRowDetails(1, GRID_ITEM_MARGIN,
			180));

		rowConfigurationLandscape = new ArrayList<GridRowDetails>();
		rowConfigurationLandscape.add(new GridRowDetails(3, GRID_ITEM_MARGIN,
			100));
		rowConfigurationLandscape.add(new GridRowDetails(1, GRID_ITEM_MARGIN,
			160));
	}

}
