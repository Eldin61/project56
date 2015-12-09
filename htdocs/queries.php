<?php
$queries = array(
    'SELECT DISTINCT unitid FROM events' 
	=> 'unitid',
			'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 14100071 
			AND positions.unitid = 14100071;' 
	=> '14100071',
			'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 14100026 
			AND positions.unitid = 14100026;' 
	=> '14120026',
				'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 14100031 
			AND positions.unitid = 14100031;' 
	=> '14120031',
				'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 14100029 
			AND positions.unitid = 14100029;' 
	=> '14120029',
				'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 15030001 
			AND positions.unitid = 15030001;' 
	=> '15030001',
				'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 14100042 
			AND positions.unitid = 14100042;' 
	=> '14100042',
				'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 999 
			AND positions.unitid = 999;' 
	=> '999',
				'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 14120032 
			AND positions.unitid = 14120032;' 
	=> '14120032',
					'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 14100064 
			AND positions.unitid = 14100064;' 
	=> '14100064',
					'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 14100015 
			AND positions.unitid = 14100015;' 
	=> '14100015',
					'SELECT *
			FROM events
			INNER JOIN positions
			ON events.datetime=positions.datetime 
			AND events.unitid = 15030000 
			AND positions.unitid = 15030000;' 
	=> '15030000',
	);