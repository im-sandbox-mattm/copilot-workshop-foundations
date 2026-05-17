import React from "react";

type TimelineItem = {
    id: string;
    label: string;
    status: string;
    highlight?: boolean;
};

type LegacyOwnerTimelineProps = {
    ownerName: string;
    filterText?: string;
    items: TimelineItem[];
};

type LegacyOwnerTimelineState = {
    visibleItems: TimelineItem[];
    filterText: string;
    showHighlightedOnly: boolean;
    statusMessage: string;
};

class LegacyOwnerTimeline extends React.Component<LegacyOwnerTimelineProps, LegacyOwnerTimelineState> {
    constructor(props: LegacyOwnerTimelineProps) {
        super(props);
        this.state = {
            visibleItems: [],
            filterText: props.filterText || "",
            showHighlightedOnly: false,
            statusMessage: "Preparing timeline..."
        };
    }

    componentDidMount() {
        this.applyFilters(this.props, this.state.showHighlightedOnly, this.state.filterText);
    }

    componentDidUpdate(prevProps: LegacyOwnerTimelineProps, prevState: LegacyOwnerTimelineState) {
        if (
            prevProps.items !== this.props.items ||
            prevProps.filterText !== this.props.filterText ||
            prevState.showHighlightedOnly !== this.state.showHighlightedOnly ||
            prevState.filterText !== this.state.filterText
        ) {
            this.applyFilters(this.props, this.state.showHighlightedOnly, this.state.filterText);
        }
    }

    applyFilters(props: LegacyOwnerTimelineProps, showHighlightedOnly: boolean, filterText: string) {
        let nextItems = props.items.slice();
        let normalizedFilter = filterText.trim().toLowerCase();

        if (normalizedFilter.length > 0) {
            nextItems = nextItems.filter(function (item) {
                return item.label.toLowerCase().indexOf(normalizedFilter) >= 0
                    || item.status.toLowerCase().indexOf(normalizedFilter) >= 0;
            });
        }

        if (showHighlightedOnly) {
            nextItems = nextItems.filter(function (item) {
                return item.highlight === true;
            });
        }

        let statusMessage = "Showing " + nextItems.length + " updates for " + props.ownerName + ".";
        if (nextItems.length === 0) {
            statusMessage = "No timeline updates match the current filter.";
        }

        this.setState({
            visibleItems: nextItems,
            statusMessage: statusMessage
        });
    }

    handleFilterChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({ filterText: event.target.value });
    };

    toggleHighlightedOnly = () => {
        this.setState({ showHighlightedOnly: !this.state.showHighlightedOnly });
    };

    render() {
        return (
            <section>
                <h2>{this.props.ownerName} timeline</h2>

                <label>
                    Search timeline
                    <input value={this.state.filterText} onChange={this.handleFilterChange} />
                </label>

                <label>
                    <input
                        type="checkbox"
                        checked={this.state.showHighlightedOnly}
                        onChange={this.toggleHighlightedOnly}
                    />
                    Show highlighted updates only
                </label>

                <p>{this.state.statusMessage}</p>

                <ul>
                    {this.state.visibleItems.map(function (item) {
                        return (
                            <li key={item.id}>
                                <strong>{item.label}</strong>
                                <span> - {item.status}</span>
                            </li>
                        );
                    })}
                </ul>
            </section>
        );
    }
}

export default LegacyOwnerTimeline;