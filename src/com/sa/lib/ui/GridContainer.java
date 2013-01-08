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
package com.sa.lib.ui;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.sa.lib.gridcontainer.R;

/**
 * This class provides the capability of laying out views in a custom grid-like
 * configuration. Currently the grid has the following properties:
 * - The rows will always use fill width.
 * - One can specify the number of views to fit per row.
 * - One can specify an optional margin to use between the views.
 * - One can specify an optional height for a row. If none is specified,
 *   the row will wrap content.
 *   
 * It's also fairly easy to specify different row configurations for different
 * device orientations.
 * 
 * @author Stephen Asherson
 */
public class GridContainer extends ScrollView
{
	// Member variables
	private LinearLayout gridContainer;

	/**
	 * Constructors
	 */
	public GridContainer(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}


	public GridContainer(Context context)
	{
		super(context);
	}

	/**
	 * Bind to any views in our layout
	 */
	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();

		gridContainer = (LinearLayout) findViewById(R.id.gridContainer);
	}

	/**
	 * Add items to the grid. This method will add the views according to the
	 * configuration specified.
	 * 
	 * @param gridItems
	 *        the views to add to the grid
	 * @param rowConfiguration
	 *        a list specifying the details of each row in the grid
	 */
	public void addItemsToGrid(List<? extends View> gridItems,
		List<GridRowDetails> rowConfiguration)
	{
		addItemsToGrid(gridItems, rowConfiguration, null);
	}

	/**
	 * Add items to the grid. This method will add the views according to the
	 * configuration specified.
	 * 
	 * @param gridItems
	 *        the views to add to the grid
	 * @param rowConfiguration
	 *        a list specifying the details of each row in the grid
	 * @param onClickListener
	 *        associated an onClickListener with each view.
	 */
	public void addItemsToGrid(List<? extends View> gridItems,
		List<GridRowDetails> rowConfiguration, OnClickListener onClickListener)
	{
		// clear the items in the grid, if any
		gridContainer.removeAllViews();

		if (gridItems == null || gridItems.size() == 0
			|| rowConfiguration == null)
		{
			return;
		}

		// Add the items to the grid
		int itemsAdded = 0;
		for (GridRowDetails rowDetails : rowConfiguration)
		{
			// Validate that there are enough items left for this row
			if ((itemsAdded + rowDetails.numberOfItemsInRow) > gridItems.size())
			{
				throw new IllegalArgumentException(
					"Invalid row configuration provided. Please ensure that the number of items"
						+ " specified in the configuration matches that of the grid items provided.");
			}

			// create the row container
			LinearLayout rowLayout = new LinearLayout(getContext());
			LayoutParams rowParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			rowLayout.setLayoutParams(rowParams);

			// Add the items to the row container
			// The views will have a width of 0 and equal weighting to use up the 
			// space on the screen. If spacing is desired, margins can be added to each
			// view
			for (int itemNumberInRow = 0; itemNumberInRow < rowDetails.numberOfItemsInRow; itemNumberInRow++)
			{
				View itemView = gridItems.get(itemsAdded);

				if (onClickListener != null)
				{
					itemView.setOnClickListener(onClickListener);
				}

				ViewGroup.LayoutParams oldParams = itemView.getLayoutParams();
				LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
					oldParams);
				itemParams.width = 0;
				itemParams.weight = 1.0f;
				
				// if a row height has been set, set the height of the items added
				// to the row
				if (rowDetails.rowHeight != GridRowDetails.NONE)
				{
					itemParams.height = convertDpToPx(rowDetails.rowHeight);
				}

				if (rowDetails.itemMargin != GridRowDetails.NONE
					&& oldParams instanceof ViewGroup.MarginLayoutParams)
				{
					itemParams.setMargins(rowDetails.itemMargin,
						rowDetails.itemMargin, rowDetails.itemMargin,
						rowDetails.itemMargin);
				}

				itemView.setLayoutParams(itemParams);

				rowLayout.addView(itemView);
				itemsAdded++;
			}

			gridContainer.addView(rowLayout);
		}
	}
	
	/**
	 * Utility method to convert dp units into pixels. 
	 */
	private int convertDpToPx(int dp)
	{
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		return (int)((dp * displayMetrics.density) + 0.5);
	}

	/**
	 * This class contains the details for each row. It can be used
	 * to specify how many views fit on a row, an optional margin between each view
	 * and an optional fixed height to apply to the row. 
	 */
	public static class GridRowDetails
	{
		public static final int NONE = -1;
		
		public int numberOfItemsInRow; // number of views to fit in this row.
		public int itemMargin = NONE; // optional margin between the views.
		public int rowHeight = NONE; // optional fixed height for the row, specified in DP.
		
		public GridRowDetails(int numberOfItemsInRow, int itemMargin, int rowHeight)
		{
			this.numberOfItemsInRow = numberOfItemsInRow;
			this.itemMargin = itemMargin;
			this.rowHeight = rowHeight;
		}
	}

}
