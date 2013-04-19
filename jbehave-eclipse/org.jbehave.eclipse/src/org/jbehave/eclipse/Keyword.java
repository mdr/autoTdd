package org.jbehave.eclipse;

import org.autoTdd.steps.AutoTddStepTypes;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.steps.StepType;
import org.jbehave.eclipse.util.Filter;

public enum Keyword {
	Meta {
		@Override
		public String asString(Keywords keywords) {
			return keywords.meta();
		}
	},
	MetaProperty {
		@Override
		public String asString(Keywords keywords) {
			return keywords.metaProperty();
		}
	},
	Narrative {
		@Override
		public String asString(Keywords keywords) {
			return keywords.narrative();
		}
	},
	InOrderTo {
		@Override
		public String asString(Keywords keywords) {
			return keywords.inOrderTo();
		}
	},
	AsA {
		@Override
		public String asString(Keywords keywords) {
			return keywords.asA();
		}
	},
	IWantTo {
		@Override
		public String asString(Keywords keywords) {
			return keywords.iWantTo();
		}
	},
	Scenario {
		@Override
		public String asString(Keywords keywords) {
			return keywords.scenario();
		}
	},
	GivenStories {
		@Override
		public String asString(Keywords keywords) {
			return keywords.givenStories();
		}
	},
	ExamplesTable {
		@Override
		public String asString(Keywords keywords) {
			return keywords.examplesTable();
		}
	},
	ExamplesTableRow {
		@Override
		public String asString(Keywords keywords) {
			return keywords.examplesTableRow();
		}
	},
	ExamplesTableHeaderSeparator {
		@Override
		public String asString(Keywords keywords) {
			return keywords.examplesTableHeaderSeparator();
		}
	},
	ExamplesTableValueSeparator {
		@Override
		public String asString(Keywords keywords) {
			return keywords.examplesTableValueSeparator();
		}
	},
	ExamplesTableIgnorableSeparator {
		@Override
		public String asString(Keywords keywords) {
			return keywords.examplesTableIgnorableSeparator();
		}
	},
	Given {
		@Override
		public String asString(Keywords keywords) {
			return keywords.given();
		}
	},
	When {
		@Override
		public String asString(Keywords keywords) {
			return keywords.when();
		}
	},
	Then {
		@Override
		public String asString(Keywords keywords) {
			return keywords.then();
		}
	},
	And {
		@Override
		public String asString(Keywords keywords) {
			return keywords.and();
		}
	},
	Ignorable {
		@Override
		public String asString(Keywords keywords) {
			return keywords.ignorable();
		}
	},
	Because {
		@Override
		public String asString(Keywords keywords) {
			return keywords.startingWord("Because ");
		}
	},
	Called {
		@Override
		public String asString(Keywords keywords) {
			return keywords.startingWord("Called ");
		}
	};

	private static Keywords keywords = KeywordsCustomizer.customize(new Keywords());
		

	public String asString() {
		return asString(keywords);
	}

	public String asString(Keywords keywords) {
		throw new AbstractMethodError();
	}

	public boolean isStep() {
		return isStep(this);
	}

	public boolean isNarrative() {
		return isNarrative(this);
	}

	public boolean isExampleTable() {
		return isExampleTable(this);
	}

	public boolean isComment() {
		return this == Ignorable;
	}

	public static Keyword lookup(StringBuilder builder, Keywords keywords) {
		int len = builder.length();
		for (Keyword jk : values()) {
			String asString = jk.asString(keywords);
			if (asString.length() != builder.length()) {
				continue;
			}

			boolean match = true;
			for (int i = 0; i < len && match; i++) {
				if (builder.charAt(i) != asString.charAt(i))
					match = false;
			}
			if (match) {
				return jk;
			}
		}
		return null;
	}

	public static Filter<Keyword> stepFilter() {
		return new Filter<Keyword>() {
			@Override
			public boolean isAccepted(Keyword keyword) {
				return isStep(keyword);
			}
		};
	}

	public static boolean isStep(Keyword keyword) {
		if (keyword == null)
			return false;
		switch (keyword) {
		case Given:
		case Because:
		case Called:
		case When:
		case Then:
		case And:
			return true;
		default:
			return false;
		}
	}

	public boolean isSameAs(StepType type) {
		if (type == StepType.GIVEN)
			return this == Given;
		if (type == StepType.WHEN)
			return this == When;
		if (type == StepType.THEN)
			return this == Then;
		if (type == AutoTddStepTypes.BECAUSE)
			return this == Because;
		if (type == AutoTddStepTypes.CALLED)
			return this == Called;
		return false;
	}

	public static boolean isNarrative(Keyword keyword) {
		if (keyword == null)
			return false;
		switch (keyword) {
		case Narrative:
		case InOrderTo:
		case AsA:
		case IWantTo:
			return true;
		default:
			return false;
		}
	}

	public static boolean isExampleTable(Keyword keyword) {
		if (keyword == null)
			return false;
		switch (keyword) {
		case ExamplesTable:
		case ExamplesTableHeaderSeparator:
		case ExamplesTableIgnorableSeparator:
		case ExamplesTableRow:
		case ExamplesTableValueSeparator:
			return true;
		default:
			return false;
		}
	}

}
