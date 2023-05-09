import React from "react"
import { timeFormat, scaleLinear, max, min, axisLeft, axisBottom, select } from "d3"

export default class ScatterPlot extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: null,
        };
    }

    async componentWillMount() {
        try {
            const response = await fetch('http://localhost:8080/weather');
            console.log(response);
            const data = await response.json();
            console.log(data);
            this.setState({ data });
        } catch (err) {
            console.error(err);
        }
    }

    render() {
        const margin = { top: 20, right: 15, bottom: 60, left: 60 }
        const width = 800 - margin.left - margin.right
        const height = 600 - margin.top - margin.bottom
        const data = this.state.data;
        console.log(data);
        if (data == null) {
            return;
        }

        const x = scaleLinear()
            .domain([
                min(data, (d) => new Date(d.window.end)),
                max(data, (d) => new Date(d.window.end))
            ])
            .range([0, width])

        const y = scaleLinear()
            .domain([
                min(data, (d) => d.avg_temp_c) - 1,
                max(data, (d) => d.avg_temp_c) + 1
            ])
            .range([height, 0])
        
            const currentDate = new Date();

        return (
            <div>
                <h3> Average Temperature - {currentDate.toDateString()} </h3>
                <svg
                    width={width + margin.right + margin.left}
                    height={height + margin.top + margin.bottom}
                    className="chart"
                >
                    <g
                        transform={"translate(" + margin.left + "," + margin.top + ")"}
                        width={width}
                        height={height}
                        className="main"
                    >
                        <RenderCircles data={data} scale={{ x, y }} />
                        <Axis
                            axis="x"
                            transform={"translate(0," + height + ")"}
                            scale={axisBottom().scale(x).tickFormat(timeFormat("%H:%M:%S"))}
                        />
                        <Axis
                            axis="y"
                            transform="translate(0,0)"
                            scale={axisLeft().scale(y)}
                        />
                    </g>
                </svg>
            </div>
        )
    }
}

class RenderCircles extends React.Component {
    render() {
        let renderCircles = this.props.data.map((point, i) => (
            <circle
                cx={this.props.scale.x(new Date(point.window.end))}
                cy={this.props.scale.y(point.avg_temp_c)}
                r="8"
                style={{ fill: "rgba(25, 158, 199, .9)" }}
                key={i}
            />
        ))
        return <g>{renderCircles}</g>
    }
}

class Axis extends React.Component {
    componentDidMount() {
        const node = this.refs[this.props.axis]
        select(node).call(this.props.scale)
    }

    render() {
        return (
            <g
                className="main axis date"
                transform={this.props.transform}
                ref={this.props.axis}
            />
        )
    }
}