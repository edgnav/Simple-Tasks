export class Event {
  public name: string;
  public description: string;
  public dateFrom: Date;
  public dateTo: Date;
  public userList: string[];
  public tags: { tag: string }[];

  constructor(
    name: string,
    description: string,
    dateFrom: Date,
    dateTo: Date,
    userList: string[],
    tags: { tag: string }[]
  ) {
    this.name = name;
    this.description = description;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.userList = userList;
    this.tags = tags;
  }
}
