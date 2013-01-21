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
 * the row will wrap content.
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

				if (oldParams instanceof ViewGroup.MarginLayoutParams)
				{
					itemParams.setMargins(rowDetails.itemMarginLeft,
						rowDetails.itemMarginTop, rowDetails.itemMarginRight,
						rowDetails.itemMarginBottom);
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
		DisplayMetrics displayMetrics = getContext().getResources()
			.getDisplayMetrics();
		return (int) ((dp * displayMetrics.density) + 0.5);
	}

	/**
	 * This class contains the details for each row. It can be used
	 * to specify how many views fit on a row, an optional margin between each
	 * view
	 * and an optional fixed height to apply to the row.
	 */
	public static class GridRowDetails
	{
		public static final int NONE = -1;

		public int numberOfItemsInRow; // number of views to fit in this row.
		public int itemMarginTop = 0; // optional top margin between the views.
		public int itemMarginBottom = 0; // optional bottom margin between the views.
		public int itemMarginLeft = 0; // optional left margin between the views.
		public int itemMarginRight = 0; // optional right margin between the views.
		public int rowHeight = NONE; // optional fixed height for the row, specified in DP.

		public GridRowDetails(int numberOfItemsInRow, int itemMargin,
			int rowHeight)
		{
			this.numberOfItemsInRow = numberOfItemsInRow;
			this.itemMarginTop = itemMargin;
			this.itemMarginBottom = itemMargin;
			this.itemMarginLeft = itemMargin;
			this.itemMarginRight = itemMargin;
			this.rowHeight = rowHeight;
		}
		
		public GridRowDetails(int numberOfItemsInRow, int rowHeight)
		{
			this.numberOfItemsInRow = numberOfItemsInRow;
			this.rowHeight = rowHeight;
		}
		
		/**
		 * Set margins for each side.
		 */
		public void setMargins(int leftMargin, int topMargin, int rightMargin, int bottomMargin)
		{
			this.itemMarginTop = topMargin;
			this.itemMarginBottom = rightMargin;
			this.itemMarginLeft = leftMargin;
			this.itemMarginRight = rightMargin;
		}
	}
}
